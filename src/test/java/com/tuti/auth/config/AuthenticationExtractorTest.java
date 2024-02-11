package com.tuti.auth.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class AuthenticationExtractorTest {

    @DisplayName("JWT 토큰을 받았을 때 payload를 반환한다.")
    @Test
    void getPayload() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer token-value");

        String expected = AuthenticationExtractor.extract(mockHttpServletRequest);

        Assertions.assertThat(expected).isEqualTo("token-value");
    }

}
