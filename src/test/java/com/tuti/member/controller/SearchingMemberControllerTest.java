package com.tuti.member.controller;


import com.tuti.common.ControllerTest;
import com.tuti.member.controller.SearchingMemberController;
import com.tuti.member.service.SearchingMemberService;
import com.tuti.member.service.exception.MemberNotFoundException;
import com.tuti.member.service.response.MemberDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SearchingMemberController.class)
public class SearchingMemberControllerTest extends ControllerTest {

    @MockBean
    SearchingMemberService searchingMemberService;

    @DisplayName("유효하지 않은 토큰으로 권한이 필요한 요청 시 401 에러 반환")
    @ParameterizedTest
    @ValueSource(strings = {"Bearer invalidToken", "aaasssddd"})
    void invalidToken(String token) throws Exception {
        // when, then
        mockMvc.perform(get("/my-page")
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("401"));
    }

    @DisplayName("토큰 형식은 올바르나 사용자가 없는 경우 404 에러 반환")
    @Test
    void notExistMember() throws Exception {
        // given
        String accessToken = jwtTokenProvider.createAccessToken(999L);
        given(searchingMemberService.getMember(any())).willThrow(new MemberNotFoundException());

        // when, then
        mockMvc.perform(get("/my-page")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("회원을 찾을 수 없습니다."));
    }

    @DisplayName("유저 상세 조회는 로그인된 회원만 가능하다")
    @Test
    void getMemberDetailOnlyLoginMember() throws Exception {
        // when, then
        mockMvc.perform(get("/member/{memberId}", 1L)
                        .param("memberId", "1"))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("401"))
                .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.message").value("유효하지 않은 토큰입니다."));
    }

    @DisplayName("로그인된 회원은 유저 상세 조회가 가능하다")
    @Test
    void getMember() throws Exception {
        // given
        String accessToken = jwtTokenProvider.createAccessToken(1L);

        when(searchingMemberService.getMember(any())).thenReturn(MemberDetailResponse.builder().build());

        // when, then
        mockMvc.perform(get("/member/{memberId}", 1L)
                        .param("memberId", "1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"));
    }

    @DisplayName("메인 페이지 조회(전체 회원 프로필 리스트업)는 비회원, 회원 모두 가능하다")
    @Test
    void getMembers() throws Exception {
        // when, then
        mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"));
    }

}
