package com.tuti.member.controller;

import com.tuti.auth.config.AuthenticatedMemberId;
import com.tuti.member.service.MemberService;
import com.tuti.member.service.request.EnterpriseJoinRequest;
import com.tuti.member.service.request.StudentJoinRequest;
import com.tuti.member.service.request.ProfileRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member Controller")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "일반 회원 가입", description = "일반 회원 가입을 진행(필드 값 입력 시 이메일은 형식 맞춰서, gender는 female or F, male or M으로 입력해야 함)")
    @GetMapping("/join/user")
    public ResponseEntity<Void> joinStudent(@Valid @RequestBody StudentJoinRequest studentJoinRequest) {
        memberService.joinStudent(studentJoinRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "기업 회원 가입", description = "기업 회원 가입을 진행(필드 값 입력 시 이메일은 형식 맞춰서, gender는 female or F, male or M으로 입력해야 함)")
    @GetMapping("/join/enterprise")
    public ResponseEntity<Void> joinEnterprise(@Valid @RequestBody EnterpriseJoinRequest enterpriseJoinRequest) {
        memberService.joinEnterprise(enterpriseJoinRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "프로필 수정", description = "프로필 정보를 수정한다")
    @PatchMapping("/myProfile")
    public ResponseEntity<Void> getProfile(@Parameter(hidden = true) @AuthenticatedMemberId Long memberId, ProfileRequest profileRequest) {
        memberService.updateProfile(memberId, profileRequest);
        return ResponseEntity.noContent().build();
    }
}
