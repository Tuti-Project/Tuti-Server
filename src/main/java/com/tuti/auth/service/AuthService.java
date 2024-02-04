package com.tuti.auth.service;

import com.tuti.auth.infrastructure.JwtTokenProvider;
import com.tuti.auth.infrastructure.OAuthAttributes;
import com.tuti.auth.infrastructure.OAuthProvider;
import com.tuti.auth.service.exception.InvalidEmailOrPasswordException;
import com.tuti.auth.service.request.LoginRequest;
import com.tuti.auth.service.response.AccessTokenResponse;
import com.tuti.auth.service.response.OAuthUserProfile;
import com.tuti.member.domain.Member;
import com.tuti.member.domain.repository.MemberRepository;
import com.tuti.member.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    private final OAuthProvider oAuthProvider;

    @Transactional
    public AccessTokenResponse oAuthLogin(String providerName, String oAuthAccessToken) {
        String userInfoUri = oAuthProvider.of(providerName);

        OAuthUserProfile oAuthUserProfile = getUserProfile(providerName, oAuthAccessToken, userInfoUri);

        Member member = saveOrLogin(oAuthUserProfile);

        return new AccessTokenResponse(tokenProvider.createAccessToken(member.getId()));
    }

    public AccessTokenResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(new Email(loginRequest.getEmail()))
                .orElseThrow(InvalidEmailOrPasswordException::new);

        if (!loginRequest.getPassword().equals(member.getPassword())) {
            throw new InvalidEmailOrPasswordException();
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

    private OAuthUserProfile getUserProfile(String providerName, String oAuthAccessToken, String userInfoUri) {
        Map<String, Object> OAuthUserAttributes = getUserAttributes(userInfoUri, oAuthAccessToken);
        return OAuthAttributes.extract(providerName, OAuthUserAttributes);
    }

    private Map<String, Object> getUserAttributes(String userInfoUri, String oAuthAccessToken) {
        return WebClient.create()
                .get()
                .uri(userInfoUri)
                .headers(header -> header.setBearerAuth(oAuthAccessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

}
