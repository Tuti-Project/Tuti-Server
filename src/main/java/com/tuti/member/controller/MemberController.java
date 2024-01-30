package com.tuti.member.controller;

import com.tuti.auth.config.AuthenticatedMemberId;
import com.tuti.common.advice.response.ApiResponse;
import com.tuti.member.service.MemberService;
import com.tuti.member.service.request.EnterpriseJoinRequest;
import com.tuti.member.service.request.UpdateMyPageRequest;
import com.tuti.member.service.request.StudentJoinRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member Controller")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "일반 회원 가입", description = "일반 회원 가입을 진행(필드 값 입력 시 이메일은 형식 맞춰서, gender는 female or F, male or M으로 입력해야 함)")
    @PostMapping("/join/user")
    public ApiResponse<Void> joinStudent(@Valid @RequestBody StudentJoinRequest studentJoinRequest) {
        memberService.joinStudent(studentJoinRequest);
        return ApiResponse.ok();
    }

    @Operation(summary = "기업 회원 가입", description = "기업 회원 가입을 진행(필드 값 입력 시 이메일은 형식 맞춰서, gender는 female or F, male or M으로 입력해야 함)")
    @PostMapping("/join/enterprise")
    public ApiResponse<Void> joinEnterprise(@Valid @RequestBody EnterpriseJoinRequest enterpriseJoinRequest) {
        memberService.joinEnterprise(enterpriseJoinRequest);
        return ApiResponse.ok();
    }

    @Operation(summary = "마이페이지 수정", description = "마이페이지 정보를 수정한다")
    @PutMapping("/my-page")
    public ApiResponse<Void> updateMyPage(@Parameter(hidden = true) @AuthenticatedMemberId Long memberId,
                                          @RequestBody UpdateMyPageRequest updateMyPageRequest) {
        memberService.updateMyPage(memberId, updateMyPageRequest);
        return ApiResponse.ok();
    }
}
