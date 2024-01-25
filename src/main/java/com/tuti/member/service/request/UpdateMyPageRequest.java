package com.tuti.member.service.request;

import com.tuti.member.domain.vo.ApplyMatchingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MyPageUpdateRequest {
    private String university;
    private String major;
    private List<String> jobTags;
    private List<String> skillTags;
    private String description;
    private ApplyMatchingStatus applyMatchingStatus;
}
