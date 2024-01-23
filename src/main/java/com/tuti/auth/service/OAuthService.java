package com.tuti.auth.service;

import com.tuti.auth.config.oauth.InMemoryProviderRepository;
import com.tuti.auth.infrastructure.JwtTokenProvider;
import com.tuti.auth.infrastructure.OAuthAttributes;
import com.tuti.auth.infrastructure.OAuthProvider;
import com.tuti.auth.service.request.LoginRequest;
import com.tuti.auth.service.response.AccessTokenResponse;
import com.tuti.auth.service.response.OAuthTokenResponse;
import com.tuti.auth.service.response.OAuthUserProfile;
import com.tuti.member.domain.Member;
import com.tuti.member.domain.repository.MemberRepository;
import com.tuti.member.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;

    private static final String GRANT_TYPE = "authorization_code";

    public AccessTokenResponse oAuthLogin(String providerName, String code) {
        OAuthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

        OAuthTokenResponse tokenResponse = getToken(code, provider);

        OAuthUserProfile oAuthUserProfile = getUserProfile(providerName, tokenResponse, provider);

        Member member = saveOrLogin(oAuthUserProfile);

        return new AccessTokenResponse(tokenProvider.createAccessToken(member.getId()));
    }

    public AccessTokenResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(new Email(loginRequest.getEmail())).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!loginRequest.getPassword().equals(member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        return new AccessTokenResponse(tokenProvider.createAccessToken(member.getId()));
    }

    private Member saveOrLogin(OAuthUserProfile oAuthUserProfile) {
        Optional<Member> member = memberRepository.findByEmail(new Email(oAuthUserProfile.getEmail()));
        if (member.isEmpty()) {
            return memberRepository.save(oAuthUserProfile.toMember());
        }
        return member.get();
    }

    private OAuthUserProfile getUserProfile(String providerName, OAuthTokenResponse tokenResponse, OAuthProvider provider) {
        Map<String, Object> OAuthUserAttributes = getUserAttributes(provider, tokenResponse);
        return OAuthAttributes.extract(providerName, OAuthUserAttributes);
    }

    private Map<String, Object> getUserAttributes(OAuthProvider provider, OAuthTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri(provider.getUserInfoUrl())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private OAuthTokenResponse getToken(String code, OAuthProvider provider) {
        return WebClient.create()
                .post()
                .uri(provider.getTokenUrl())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> tokenRequest(String code, OAuthProvider provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", GRANT_TYPE);
        formData.add("redirect_uri", provider.getRedirectUrl());
        formData.add("client_id", provider.getClientId());
        formData.add("client_secret", provider.getClientSecret());
        return formData;
    }

}
