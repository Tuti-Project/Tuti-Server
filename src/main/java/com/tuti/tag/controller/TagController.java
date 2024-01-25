package com.tuti.tag.controller;

import com.tuti.common.advice.response.ApiResponse;
import com.tuti.tag.service.TagService;
import com.tuti.tag.service.response.JobTagResponse;
import com.tuti.tag.service.response.SkillTagResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @Operation(summary = "직무 태그 정보", description = "직무 태그 정보를 가져온다")
    @GetMapping("/api/job-tags")
    public ApiResponse<JobTagResponse> getJobTags() {
        return ApiResponse.ok(tagService.findAllJobTags());
    }

    @Operation(summary = "활용 능력 태그 정보", description = "활용 능력 태그 정보를 가져온다")
    @GetMapping("/api/skill-tags")
    public ApiResponse<SkillTagResponse> getSkillTags() {
        return ApiResponse.ok(tagService.findAllSkillTags());
    }
}
