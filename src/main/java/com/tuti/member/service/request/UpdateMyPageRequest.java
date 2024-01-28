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
public class UpdateMyPageRequest {
    private String university;
    private String major;
    private String imageUrl;
    private List<String> jobTags;
    private List<String> skillTags;
    private String description;
    private ApplyMatchingStatus applyMatchingStatus;
    private String matchingDescription;
    private List<String> availableDays;
    private String availableHours;
}
