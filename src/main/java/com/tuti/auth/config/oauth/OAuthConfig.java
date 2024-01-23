package com.tuti.auth.config.oauth;

import com.tuti.auth.infrastructure.OAuthProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(OAuthProperties.class)
public class OAuthConfig {

    private final OAuthProperties properties;

    public OAuthConfig(OAuthProperties properties) {
        this.properties = properties;
    }

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        Map<String, OAuthProvider> providers = OAuthAdapter.getOAuthProviders(properties);
        return new InMemoryProviderRepository(providers);
    }
}
