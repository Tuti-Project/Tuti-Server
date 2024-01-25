package com.tuti.member.controller;

import com.tuti.auth.config.AuthenticatedMemberId;
import com.tuti.common.advice.response.ApiResponse;
import com.tuti.member.service.SearchingMemberService;
import com.tuti.member.service.response.MembersResponse;
import com.tuti.member.service.response.MyPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Searching Member Controller")
public class SearchingMemberController {

    private final SearchingMemberService searchingMemberService;

    @Operation(summary = "메인 페이지", description = "회원 프로필을 리스트업")
    @GetMapping("/home")
    public ApiResponse<MembersResponse> listUpMember(@RequestParam(value = "page", defaultValue = "0") int page) {
        return ApiResponse.ok(searchingMemberService.getMembers(page));
    }

    @Operation(summary = "마이페이지 정보", description = "마이페이지 정보를 가져온다")
    @GetMapping("/my-page")
    public ApiResponse<MyPageResponse> getMyPage(@Parameter(hidden = true) @AuthenticatedMemberId Long memberId) {
        return ApiResponse.ok(searchingMemberService.getMyPage(memberId));
    }

}
