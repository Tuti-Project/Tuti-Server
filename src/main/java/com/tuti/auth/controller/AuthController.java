package com.tuti.auth.controller;

import com.tuti.auth.service.OAuthService;
import com.tuti.auth.service.request.LoginRequest;
import com.tuti.auth.service.response.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuthService oAuthService;

    @GetMapping("/login/oauth/{provider}")
    public ResponseEntity<AccessTokenResponse> oAuthLogin(@PathVariable String provider, @RequestParam String code) {
        AccessTokenResponse loginResponse = oAuthService.oAuthLogin(provider, code);
        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(oAuthService.login(loginRequest));
    }

}
