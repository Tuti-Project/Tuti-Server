package com.tuti.member.service.request;

import com.tuti.member.domain.vo.ApplyMatchingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UpdateMyPageRequest {
    private String university;
    private String major;
    private String imageUrl;
    private Set<String> jobTags;
    private Set<String> skillTags;
    private String description;
    private ApplyMatchingStatus applyMatchingStatus;
    private String matchingDescription;
    private Set<String> availableDays;
    private String availableHours;
}
