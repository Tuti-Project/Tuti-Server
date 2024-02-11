package com.tuti.auth.infrastructure;

import com.tuti.auth.config.AuthenticationExtractor;
import com.tuti.common.ControllerTest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(value = JwtTokenProvider.class)
public class JwtTokenProviderTest extends ControllerTest {

    private static final String TEST_SECRET_KEY = "9d0bd354d2a68141d2ced83c26fe3fb72046783c19e7b727a45804d7d80c96a1541f9decbc3833519bd168ff7735d15a0e0737f40b20977bece9d8c0220425a1";

    @DisplayName("만료된 토큰인지 확인한다")
    @Test
    void isExpiredToken() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer token");

        String payload = AuthenticationExtractor.extract(mockHttpServletRequest);

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000);

        final String token = Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(new Date(now.getTime() - 100))
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, TEST_SECRET_KEY)
                .compact();

        assertThat(jwtTokenProvider.validateToken(token)).isFalse();
    }

    @DisplayName("유효한 토큰을 검증한다.")
    @Test
    void validateTokenByValidToken() {
        String token = jwtTokenProvider.createAccessToken(1L);

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @DisplayName("유효하지 않은 토큰을 검증한다.")
    @Test
    void validateTokenByInvalidToken() {
        String token = jwtTokenProvider.createAccessToken(1L);

        String invalidToken = token + "dummy";

        assertThat(jwtTokenProvider.validateToken(invalidToken)).isFalse();
    }

    @DisplayName("JwtToken payload 검증한다.")
    @Test
    void validatePayload() {
        String token = jwtTokenProvider.createAccessToken(1L);

        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo("1");
    }

    @DisplayName("JwtToken 형식을 검증한다.")
    @Test
    void validateJwtTokenFormat() {
        String token = jwtTokenProvider.createAccessToken(1L);

        String[] parts = token.split("\\.");

        assertThat(parts).hasSize(3);
    }
}