package com.tuti.member.controller;

import com.tuti.auth.config.AuthenticatedMemberId;
import com.tuti.common.advice.response.ApiResponse;
import com.tuti.member.service.SearchingMemberService;
import com.tuti.member.service.response.MemberDetailResponse;
import com.tuti.member.service.response.MembersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Searching Member Controller")
public class SearchingMemberController {

    private final SearchingMemberService searchingMemberService;

    @Operation(summary = "메인 페이지", description = "회원 프로필을 리스트업")
    @GetMapping("/home")
    public ApiResponse<MembersResponse> listUpMember(@RequestParam(value = "last", defaultValue = "0") Long lastMemberId) {
        return ApiResponse.ok(searchingMemberService.getMembers(lastMemberId));
    }

    @Operation(summary = "마이페이지 정보", description = "마이페이지 정보를 가져온다")
    @GetMapping("/my-page")
    public ApiResponse<MemberDetailResponse> getMyPage(@Parameter(hidden = true) @AuthenticatedMemberId Long memberId) {
        return ApiResponse.ok(searchingMemberService.getMyPage(memberId));
    }

    @Operation(summary = "유저 상세 페이지", description = "유저 상세 페이지 정보를 가져온다")
    @GetMapping("/member/{memberId}")
    public ApiResponse<MemberDetailResponse> getMember(@Parameter(hidden = true) @AuthenticatedMemberId Long memberId, @RequestParam("memberId") Long findMemberId) {
        return ApiResponse.ok(searchingMemberService.getMember(memberId, findMemberId));
    }

}
