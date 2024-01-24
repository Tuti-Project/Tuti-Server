package com.tuti.auth.controller;

import com.tuti.auth.service.OAuthService;
import com.tuti.auth.service.request.LoginRequest;
import com.tuti.auth.service.response.AccessTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth Controller")
public class AuthController {

    private final OAuthService oAuthService;

    @Operation(summary = "OAuth 로그인", description = "provider는 kakao 혹은 naver 입력, code는 kakao와 네이버 로그인 API에서 받은 code 갑 입력")
    @GetMapping("/login/oauth/{provider}")
    public ResponseEntity<AccessTokenResponse> oAuthLogin(@PathVariable(name = "provider") String provider, @RequestParam("code") String code) {
        AccessTokenResponse loginResponse = oAuthService.oAuthLogin(provider, code);
        return ResponseEntity.ok().body(loginResponse);
    }

    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호를 입력하여 로그인")
    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(oAuthService.login(loginRequest));
    }

}
