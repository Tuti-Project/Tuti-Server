package com.tuti.auth.controller;

import com.tuti.auth.controller.interceptor.AuthenticationInterceptor;
import com.tuti.auth.service.exception.InvalidTokenException;
import com.tuti.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@WebMvcTest(value = AuthenticationInterceptor.class)
public class AuthenticationInterceptorTest extends ControllerTest {

    @DisplayName("토큰이 유효한 지 검증한다.")
    @Test
    void validateToken() throws Exception {
        // given, when
        String token = jwtTokenProvider.createAccessToken(1L);
        String bearerToken = "Bearer " + token;

        given(httpServletRequest.getMethod())
                .willReturn(HttpMethod.GET.toString());
        given(httpServletRequest.getRequestURI())
                .willReturn("/my-page");
        given(httpServletRequest.getHeaders(HttpHeaders.AUTHORIZATION))
                .willReturn(Collections.enumeration(List.of(bearerToken)));

        given(httpServletRequest.getAttribute("payload")).willReturn("1");

        // then
        assertThat(httpServletRequest.getAttribute("payload")).isEqualTo(jwtTokenProvider.getPayload(token));
    }

    @DisplayName("유효하지 않은 토큰일 경우 예외를 던진다.")
    @Test
    void validateInvalidToken() {
        String token = "Bearer " + "Invalid token";

        given(httpServletRequest.getMethod())
                .willReturn(HttpMethod.GET.toString());
        given(httpServletRequest.getRequestURI())
                .willReturn("/my-page");
        given(httpServletRequest.getHeaders(HttpHeaders.AUTHORIZATION))
                .willReturn(Collections.enumeration(List.of(token)));

        assertThatThrownBy(() -> authenticationInterceptor.preHandle(httpServletRequest, null, null))
                .isInstanceOf(InvalidTokenException.class);
    }

}
