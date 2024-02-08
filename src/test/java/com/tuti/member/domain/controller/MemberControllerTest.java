package com.tuti.member.domain.controller;

import com.tuti.common.ControllerTest;
import com.tuti.fixtures.MemberFixtures;
import com.tuti.member.controller.MemberController;
import com.tuti.member.service.MemberService;
import com.tuti.member.service.exception.MemberNotFoundException;
import com.tuti.member.service.request.EnterpriseJoinRequest;
import com.tuti.member.service.request.StudentJoinRequest;
import com.tuti.member.service.request.UpdateMyPageRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MemberController.class)
public class MemberControllerTest extends ControllerTest {

    @MockBean
    MemberService memberService;

    @DisplayName("일반 회원 가입 시 모든 필드는 빈 값이거나 null 일 수 없다")
    @Test
    void joinStudentFailWithInvalidRequst() throws Exception {
        // given
        StudentJoinRequest request = StudentJoinRequest.builder().build();

        // when, then
        mockMvc.perform(post("/join/user")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("입력 형식 또는 타입이 올바르지 않습니다."));

    }

    @DisplayName("기업 회원 가입 시 모든 필드는 빈 값이거나 null 일 수 없다")
    @Test
    void joinEnterpriseFailWithInvalidRequest() throws Exception {
        // given
        EnterpriseJoinRequest request = EnterpriseJoinRequest.builder().build();

        // when, then
        mockMvc.perform(post("/join/enterprise")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("입력 형식 또는 타입이 올바르지 않습니다."));

    }

    @DisplayName("일반 회원 가입 시 모든 필드가 올바르면 회원 가입에 성공한다")
    @Test
    void joinStudentSuccess() throws Exception {
        // given
        StudentJoinRequest request = createJoinStudentRequest();

        // when, then
        mockMvc.perform(post("/join/user")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"));
    }

    @DisplayName("기업 회원 가입 시 모든 필드가 올바르면 회원 가입에 성공한다")
    @Test
    void joinEnterpriseSuccess() throws Exception {
        // given
        EnterpriseJoinRequest request = createJoinEnterpriseRequest();

        // when, then
        mockMvc.perform(post("/join/enterprise")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"));
    }

    @DisplayName("마이페이지 수정은 로그인된 회원만 가능하다")
    @Test
    void successUpdateMyPage() throws Exception {
        // given
        String accessToken = jwtTokenProvider.createAccessToken(1L);
        UpdateMyPageRequest request = UpdateMyPageRequest.builder().build();

        // when, then
        mockMvc.perform(put("/my-page")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"));
    }

    @DisplayName("로그인된 유저를 찾을 수 없으면 마이페이지를 수정할 수 없다")
    @Test
    void failUpdateMyPageWithNotFoundMember() throws Exception {
        // given
        String accessToken = jwtTokenProvider.createAccessToken(1L);
        UpdateMyPageRequest request = UpdateMyPageRequest.builder().build();

        doThrow(new MemberNotFoundException()).when(memberService).updateMyPage(any(), any());

        // when, then
        mockMvc.perform(put("/my-page")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("404"));
    }

    private EnterpriseJoinRequest createJoinEnterpriseRequest() {
        return EnterpriseJoinRequest.builder()
                .email(MemberFixtures.정우_이메일.getValue())
                .name(MemberFixtures.정우_이름)
                .gender("M")
                .password(MemberFixtures.정우_비밀번호)
                .birthYear(MemberFixtures.정우_생년)
                .birthDay(MemberFixtures.정우_생일)
                .businessNumber("123-45-789456")
                .build();
    }


    private StudentJoinRequest createJoinStudentRequest() {
        return StudentJoinRequest.builder()
                .email(MemberFixtures.정우_이메일.getValue())
                .name(MemberFixtures.정우_이름)
                .gender("M")
                .password(MemberFixtures.정우_비밀번호)
                .birthYear(MemberFixtures.정우_생년)
                .birthDay(MemberFixtures.정우_생일)
                .build();
    }

}
