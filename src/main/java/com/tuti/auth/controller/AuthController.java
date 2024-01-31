package com.tuti.auth.controller;

import com.tuti.auth.service.AuthService;
import com.tuti.auth.service.request.LoginRequest;
import com.tuti.auth.service.response.AccessTokenResponse;
import com.tuti.common.advice.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth Controller")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "OAuth 로그인", description = "provider는 kakao 혹은 naver 입력, code는 kakao와 네이버 로그인 API에서 받은 token 값 입력")
    @PostMapping("/login/oauth/{provider}")
    public ApiResponse<AccessTokenResponse> oAuthLogin(@PathVariable(name = "provider") String provider, @RequestParam("code") String token) {
        return ApiResponse.ok(authService.oAuthLogin(provider, token));
    }

    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호를 입력하여 로그인")
    @PostMapping("/login")
    public ApiResponse<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.ok(authService.login(loginRequest));
    }

}
