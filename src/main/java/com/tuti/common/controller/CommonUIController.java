package com.tuti.common.controller;

import com.tuti.common.advice.response.ApiResponse;
import com.tuti.common.controller.response.TermsResponse;
import com.tuti.common.entity.terms_conditions.TermsConditions;
import com.tuti.common.entity.terms_conditions.TermsConditionsRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CommonUIController {

    private final TermsConditionsRepository termsConditionsRepository;

    @Operation(summary = "약관 데이터 조회", description = "이용 약관, 개인정보 처리방침, 환불 정책")
    @GetMapping("/terms")
    public ApiResponse<TermsResponse> getTerms() {
        TermsConditions termsConditions = termsConditionsRepository.findAll().stream().findFirst().get();
        return ApiResponse.ok(new TermsResponse(termsConditions));
    }
}
