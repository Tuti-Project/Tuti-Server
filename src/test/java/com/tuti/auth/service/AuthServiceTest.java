package com.tuti.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuti.auth.infrastructure.JwtTokenProvider;
import com.tuti.auth.infrastructure.OAuthProvider;
import com.tuti.auth.service.exception.InvalidEmailOrPasswordException;
import com.tuti.auth.service.request.LoginRequest;
import com.tuti.auth.service.response.AccessTokenResponse;
import com.tuti.auth.service.response.OAuthUserProfile;
import com.tuti.common.RepositoryTest;
import com.tuti.fixtures.MemberFixtures;
import com.tuti.member.domain.repository.MemberRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@RepositoryTest
@Import({JwtTokenProvider.class, ObjectMapper.class})
public class AuthServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    OAuthProvider oAuthProvider;

    private AuthService authService;

    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        authService = new AuthService(memberRepository, jwtTokenProvider, oAuthProvider, webClient);
        memberRepository.save(MemberFixtures.정우());
    }

    @AfterEach
    void terminate() throws IOException {
        mockWebServer.close();
    }

    @DisplayName("입력한 아이디와 비밀번호가 가입되어 있는 회원 정보와 일치하면 토큰을 발급한다")
    @Test
    void successLogin() {
        // given
        LoginRequest loginRequest = new LoginRequest(MemberFixtures.정우_이메일.getValue(), MemberFixtures.정우_비밀번호);

        // when
        AccessTokenResponse token = authService.login(loginRequest);

        // then
        assertThat(token).isNotNull();
    }

    @DisplayName("입력한 아이디와 비밀번호가 가입되어 있는 회원 정보와 일치하지 않으면 예외를 발생시킨다")
    @Test
    void failLoginWithNotMatched() {
        // given
        LoginRequest loginRequest = new LoginRequest(MemberFixtures.정우_이메일.getValue(), "invalid password");

        // when, then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(InvalidEmailOrPasswordException.class);
    }

    @DisplayName("입력한 아이디와 비밀번호로 가입된 회원이 없으면 예외를 발생시킨다")
    @Test
    void failLoginWithNotFound() {
        // given
        LoginRequest loginRequest = new LoginRequest("notExist@naver.com", "invalid password");

        // when, then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(InvalidEmailOrPasswordException.class);
    }

    @DisplayName("naver 로그인 시 유저 정보를 가져올 수 있다")
    @Test
    void getUserAttributesWithNaver() throws JsonProcessingException {
        // given
        Map<String, Object> response = Map.of("gender", "M", "email", "acg6138@naver.com",
                "name", "이정우", "birthday", "02-25", "birthyear", "1999");

        Map<String, Object> result = Map.of("resultcode", "00", "message", "success", "response", response);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(result))
                .addHeader("Content-Type", "application/json"));

        // when
        Map<String, Object> profileResponse = authService.getUserAttributes("naver-user-info-uri","accessToken");

        // then
        Assertions.assertThat(profileResponse).usingRecursiveComparison().isEqualTo(result);
    }

    @DisplayName("kakao 로그인 시 유저 정보를 가져올 수 있다")
    @Test
    void getUserAttributesWithKakao() throws JsonProcessingException {
        // given
        Map<String, Object> kakaoAccount = Map.of("name", "이정우", "birthyear", "1999", "birthday", "0225",
                "has_gender", true, "gender", "male", "email", "acg6138@naver.com");

        Map<String, Object> result = Map.of("id", "testId", "connected_at", "testTime", "kakao_account", kakaoAccount);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(result))
                .addHeader("Content-Type", "application/json"));

        // when
        Map<String, Object> profileResponse = authService.getUserAttributes("kakao-user-info-uri","accessToken");

        // then
        Assertions.assertThat(profileResponse).usingRecursiveComparison().isEqualTo(result);
    }

    @DisplayName("naver 로그인 시 유저 정보를 가져오고, OAuthUserProfile 로 변환한다")
    @Test
    void getUserProfileWithNaver() throws JsonProcessingException {
        // given
        Map<String, Object> response = Map.of("gender", "M", "email", "acg6138@naver.com",
                "name", "이정우", "birthday", "02-25", "birthyear", "1999");

        Map<String, Object> result = Map.of("resultcode", "00", "message", "success", "response", response);
        OAuthUserProfile expectedProfile = new OAuthUserProfile("acg6138@naver.com", "이정우", "M", "1999", "0225");

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(result))
                .addHeader("Content-Type", "application/json"));

        // when
        OAuthUserProfile profile = authService.getUserProfile("naver", "accessToken", "naver-user-info-uri");

        // then
        Assertions.assertThat(profile).usingRecursiveComparison().isEqualTo(expectedProfile);
    }

    @DisplayName("kakao 로그인 시 유저 정보를 가져오고, OAuthUserProfile 로 변환한다")
    @Test
    void getUserProfileWithKakao() throws JsonProcessingException {
        // given
        Map<String, Object> kakaoAccount = Map.of("name", "이정우", "birthyear", "1999", "birthday", "0225",
                "has_gender", true, "gender", "male", "email", "acg6138@naver.com");

        Map<String, Object> result = Map.of("id", "testId", "connected_at", "testTime", "kakao_account", kakaoAccount);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(result))
                .addHeader("Content-Type", "application/json"));

        OAuthUserProfile expectedProfile = new OAuthUserProfile("acg6138@naver.com", "이정우", "male", "1999", "0225");

        // when
        OAuthUserProfile profile = authService.getUserProfile("kakao", "accessToken", "naver-user-info-uri");

        // then
        Assertions.assertThat(profile).usingRecursiveComparison().isEqualTo(expectedProfile);
    }

    @DisplayName("OAuth 로그인 시 이미 가입된 회원이라면 회원 가입을 진행하지 않고 바로 토큰을 발급한다")
    @Test
    void oAuthLoginWithAlreadyJoinedMember() throws JsonProcessingException {
        // given
        Map<String, Object> kakaoAccount = Map.of("name", "이정우", "birthyear", "1999", "birthday", "0225",
                "has_gender", true, "gender", "male", "email", MemberFixtures.정우_이메일.getValue());

        Map<String, Object> result = Map.of("id", "testId", "connected_at", "testTime", "kakao_account", kakaoAccount);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(result))
                .addHeader("Content-Type", "application/json"));

        given(oAuthProvider.of("kakao")).willReturn("kakao-user-info-uri");

        // when
        AccessTokenResponse accessTokenResponse = authService.oAuthLogin("kakao", "accessToken");

        // then
        assertAll(
                () -> assertThat(memberRepository.findAll().size()).isEqualTo(1),
                () -> assertThat(accessTokenResponse).isNotNull()
        );
    }

    @DisplayName("OAuth 로그인 시 가입되지 않은 회원이라면 회원 가입을 진행하고 토큰을 발급한다")
    @Test
    void oAuthLoginWithFirstLoginMember() throws JsonProcessingException {
        // given
        Map<String, Object> kakaoAccount = Map.of("name", "이정우", "birthyear", "1999", "birthday", "0225",
                "has_gender", true, "gender", "male", "email", "newMember@naver.com");

        Map<String, Object> result = Map.of("id", "testId", "connected_at", "testTime", "kakao_account", kakaoAccount);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(result))
                .addHeader("Content-Type", "application/json"));

        given(oAuthProvider.of("kakao")).willReturn("kakao-user-info-uri");

        // when
        AccessTokenResponse accessTokenResponse = authService.oAuthLogin("kakao", "accessToken");

        // then
        assertAll(
                () -> assertThat(memberRepository.findAll().size()).isEqualTo(2),
                () -> assertThat(accessTokenResponse).isNotNull()
        );
    }

}
