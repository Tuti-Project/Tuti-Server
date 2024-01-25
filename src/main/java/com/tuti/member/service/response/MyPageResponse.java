package com.tuti.member.service.response;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MyPageResponse {
    private static final String WOMEN = "여자";
    private static final String MEN = "남자";

    private String name;
    private int age;
    private String gender;
    private String university;
    private String major;
    private String description;
    private List<String> jobTags;
    private List<String> skillTags;
    private ApplyMatchingStatus applyMatchingStatus;

    public MyPageResponse(Member member) {
        Profile profile = member.getProfile();
        this.name = member.getName();
        this.age = LocalDate.now().getYear() - Integer.parseInt(member.getBirthYear()) + 1;
        this.gender = genderToString(member.getGender());
        this.university = profile.getUniversity();
        this.major = profile.getMajor();
        this.description = profile.getDescription();
        this.applyMatchingStatus = member.getApplyMatchingStatus();
        this.jobTags = member.getAttachedJobTags().get().stream().map(AttachedJobTag::getJobTagName).collect(Collectors.toList());
        this.skillTags = member.getAttachedSkillTags().get().stream().map(AttachedSkillTag::getSkillTagName).collect(Collectors.toList());
    }

    private String genderToString(Gender gender) {
        return (gender.equals(Gender.FEMALE)) ? WOMEN : MEN;
    }

}
