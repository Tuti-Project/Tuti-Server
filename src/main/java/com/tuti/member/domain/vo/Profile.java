package com.tuti.member.domain.vo;

import com.tuti.member.service.request.UpdateMyPageRequest;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Profile {
    public static final String BLANK = "";

    private String university;

    private String major;

    private String imageUrl;

    private String description;

    @Enumerated(EnumType.STRING)
    private ApplyMatchingStatus applyMatchingStatus;
    private String matchingDescription;

    @Embedded
    private AttachedJobTags attachedJobTags;

    @Embedded
    private AttachedSkillTags attachedSkillTags;

    @Embedded
    private AvailableDays availableDays;
    private String availableHours;

    @Builder
    public Profile(String university, String major, String imageUrl, String description, ApplyMatchingStatus applyMatchingStatus, String matchingDescription, AttachedJobTags attachedJobTags, AttachedSkillTags attachedSkillTags, AvailableDays availableDays, String availableHours) {
        this.university = university;
        this.major = major;
        this.imageUrl = imageUrl;
        this.description = description;
        this.applyMatchingStatus = applyMatchingStatus;
        this.matchingDescription = matchingDescription;
        this.attachedJobTags = attachedJobTags;
        this.attachedSkillTags = attachedSkillTags;
        this.availableDays = availableDays;
        this.availableHours = availableHours;
    }

    public static Profile create() {
        return Profile.builder()
                .major(BLANK)
                .university(BLANK)
                .imageUrl(BLANK)
                .description(BLANK)
                .applyMatchingStatus(ApplyMatchingStatus.ON)
                .matchingDescription(BLANK)
                .availableHours(BLANK)
                .attachedJobTags(new AttachedJobTags(Collections.EMPTY_SET))
                .attachedSkillTags(new AttachedSkillTags(Collections.EMPTY_SET))
                .availableDays(new AvailableDays(Collections.EMPTY_SET))
                .build();
    }

    public void update(UpdateMyPageRequest updateMyPageRequest) {
        this.university = updateMyPageRequest.getUniversity();
        this.major = updateMyPageRequest.getMajor();
        this.imageUrl = updateMyPageRequest.getImageUrl();
        this.description = updateMyPageRequest.getDescription();
        this.applyMatchingStatus = updateMyPageRequest.getApplyMatchingStatus();
        this.matchingDescription = updateMyPageRequest.getMatchingDescription();
        this.availableHours = updateMyPageRequest.getAvailableHours();
        this.attachedJobTags = new AttachedJobTags(updateMyPageRequest.getJobTags().stream()
                .map(AttachedJobTag::new)
                .collect(Collectors.toSet()));
        this.attachedSkillTags = new AttachedSkillTags(updateMyPageRequest.getSkillTags().stream()
                .map(AttachedSkillTag::new)
                .collect(Collectors.toSet()));
        this.availableDays = new AvailableDays(updateMyPageRequest.getAvailableDays().stream()
                .map(DayOfWeek::of)
                .collect(Collectors.toSet()));
    }
}
