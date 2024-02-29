package com.tuti.auth.controller.interceptor;

import com.tuti.auth.config.AuthenticationExtractor;
import com.tuti.auth.infrastructure.JwtTokenProvider;
import com.tuti.auth.service.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider tokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        final String token = AuthenticationExtractor.extract(request);
        validateToken(token);
        return true;
    }

    private void validateToken(final String token) {
        if (token == null || !tokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }
}