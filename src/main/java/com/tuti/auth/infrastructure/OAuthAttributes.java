package com.tuti.auth.infrastructure;

import com.tuti.auth.service.response.OAuthUserProfile;

import java.util.Arrays;
import java.util.Map;

public enum OAuthAttributes {

    KAKAO("kakao") {
        @Override
        public OAuthUserProfile of(Map<String, Object> attributes) {
            Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
            System.out.println(attributes);
            return OAuthUserProfile.builder()
                    .name((String) account.get("name"))
                    .email((String) account.get("email"))
                    .gender((String) account.get("gender"))
                    .phoneNumber(((String) account.get("phone_number")).replace("+82 ", "0"))
                    .birthYear((String) account.get("birthyear"))
                    .birthDay((String) account.get("birthday"))
                    .build();
        }
    },
    NAVER("naver") {
        @Override
        public OAuthUserProfile of(Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return OAuthUserProfile.builder()
                    .email((String) response.get("email"))
                    .name((String) response.get("name"))
                    .gender((String) response.get("gender"))
                    .phoneNumber((String) response.get("mobile"))
                    .birthYear((String) response.get("birthyear"))
                    .birthDay(((String) response.get("birthday")).replace("-",""))
                    .build();
        }
    };

    private final String providerName;

    OAuthAttributes(String name) {
        this.providerName = name;
    }

    public static OAuthUserProfile extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of(attributes);
    }

    public abstract OAuthUserProfile of(Map<String, Object> attributes);
}
