package com.tuti.log.controller;

import com.tuti.common.advice.response.ApiResponse;
import com.tuti.log.service.StatisticsLoggingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Logging Controller")
public class LoggingController {
    private final StatisticsLoggingService statisticsLoggingService;

    @Operation(summary = "익일 방문자 수 저장", description = "ip를 기준으로 하루 방문자 수를 측정하고, 00시 00분마다 업데이트")
    @PostMapping("/logging/visitor-count")
    public ApiResponse<Void> updateNumberOfVisitorPerDate() {
        statisticsLoggingService.updateNumberOfVisitorPerDate();
        return ApiResponse.ok();
    }

    @Operation(summary = "익일 가입한 회원(학생, 기업)수 각각 저장")
    @PostMapping("/logging/join-count")
    public ApiResponse<Void> updateNumberOfJoinPerDate() {
        statisticsLoggingService.updateNumberOfJoinPerDate();
        return ApiResponse.ok();
    }

}
