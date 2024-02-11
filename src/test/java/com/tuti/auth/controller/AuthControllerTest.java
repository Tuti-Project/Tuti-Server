package com.tuti.auth.controller;

import com.tuti.auth.service.AuthService;
import com.tuti.auth.service.request.LoginRequest;
import com.tuti.auth.service.response.AccessTokenResponse;
import com.tuti.common.ControllerTest;
import com.tuti.fixtures.MemberFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(value = AuthController.class)
public class AuthControllerTest extends ControllerTest {

    @MockBean
    AuthService authService;

    @DisplayName("OAuth 로그인이 성공하면 토큰을 발급한다")
    @Test
    void oAuthLogin() throws Exception {
        // given
        given(authService.oAuthLogin(any(), any())).willReturn(new AccessTokenResponse("JWT Token"));

        // when, then
        mockMvc.perform(post("/login/oauth/naver?code=oauthAccessToken"))
                .andExpect(jsonPath("$.data.accessToken").value("JWT Token"))
                .andDo(print());
    }

    @DisplayName("일반 로그인이 성공하면 토큰을 발급한다")
    @Test
    void login() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest(MemberFixtures.정우_이메일.getValue(), MemberFixtures.정우_비밀번호);

        given(authService.login(any())).willReturn(new AccessTokenResponse("JWT Token"));

        // when, then
        mockMvc.perform(post("/login")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.data.accessToken").value("JWT Token"))
                .andDo(print());
    }
}
