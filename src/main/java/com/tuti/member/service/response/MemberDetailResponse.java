package com.tuti.member.service.response;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberDetailResponse {
    private static final String WOMEN = "여자";
    private static final String MEN = "남자";

    private Long memberId;
    private String name;
    private int age;
    private String gender;
    private String university;
    private String major;
    private String description;
    private Set<String> jobTags;
    private Set<String> skillTags;
    private ApplyMatchingStatus applyMatchingStatus;
    private String matchingDescription;
    private Set<String> availableDays;
    private String availableHours;

    public MemberDetailResponse(Member member) {
        Profile profile = member.getProfile();
        this.memberId = member.getId();
        this.name = member.getName();
        this.age = LocalDate.now().getYear() - Integer.parseInt(member.getBirthYear()) + 1;
        this.gender = genderToString(member.getGender());
        this.university = profile.getUniversity();
        this.major = profile.getMajor();
        this.description = profile.getDescription();
        this.applyMatchingStatus = profile.getApplyMatchingStatus();
        this.jobTags = profile.getAttachedJobTags().get().stream().map(AttachedJobTag::getJobTagName).collect(Collectors.toSet());
        this.skillTags = profile.getAttachedSkillTags().get().stream().map(AttachedSkillTag::getSkillTagName).collect(Collectors.toSet());
        this.matchingDescription = profile.getMatchingDescription();
        this.availableDays = profile.getAvailableDays().get().stream().map(DayOfWeek::getDayName).collect(Collectors.toSet());
        this.availableHours = profile.getAvailableHours();
    }

    private String genderToString(Gender gender) {
        return (gender.equals(Gender.FEMALE)) ? WOMEN : MEN;
    }
}
