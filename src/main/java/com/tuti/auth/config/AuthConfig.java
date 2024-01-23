package com.tuti.auth.config;

import com.tuti.auth.controller.interceptor.AuthenticatedMemberResolver;
import com.tuti.auth.controller.interceptor.AuthenticationInterceptor;
import com.tuti.auth.infrastructure.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final AuthenticatedMemberResolver authenticatedMemberResolver;
    private final AuthenticationInterceptor authenticationInterceptor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedMemberResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .excludePathPatterns("/api/auth/**/login")
                .excludePathPatterns("/api/auth/code/google")
                .excludePathPatterns("/api/token/reissue")
                .addPathPatterns("/test");
    }
}
