package com.tuti.auth.config.oauth;

import com.tuti.auth.infrastructure.OAuthProvider;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class OAuthAdapter {

    public static Map<String, OAuthProvider> getOAuthProviders(OAuthProperties oAuthProperties) {
        Map<String, OAuthProvider> oAuthProvider = new HashMap<>();

        oAuthProperties.getUser().forEach((key, value) ->
                oAuthProvider.put(key, new OAuthProvider(value, oAuthProperties.getProvider().get(key))));

        return oAuthProvider;
    }
}
