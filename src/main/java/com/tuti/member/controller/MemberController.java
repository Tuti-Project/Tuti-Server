package com.tuti.member.controller;

import com.tuti.member.service.MemberService;
import com.tuti.member.service.request.JoinRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    public ResponseEntity<Void> join(@Valid @RequestBody JoinRequest joinRequest) {
        memberService.join(joinRequest);
        return ResponseEntity.noContent().build();
    }
}
