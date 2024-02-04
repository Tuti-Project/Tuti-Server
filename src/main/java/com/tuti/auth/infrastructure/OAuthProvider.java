package com.tuti.auth.infrastructure;


import com.tuti.auth.service.exception.InvalidProviderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OAuthProvider {
    @Value("${oauth.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUrl;

    @Value("${oauth.provider.naver.user-info-uri}")
    private String naverUserInfoUrl;

    public String of(String providerName) {
        if (providerName.equals("kakao")) {
            return kakaoUserInfoUrl;
        }
        if (providerName.equals("naver")) {
            return naverUserInfoUrl;
        }
        throw new InvalidProviderException();
    }
}
