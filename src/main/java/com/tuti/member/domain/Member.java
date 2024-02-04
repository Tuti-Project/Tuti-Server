package com.tuti.member.domain;

import com.tuti.common.entity.BaseEntity;
import com.tuti.member.domain.vo.*;
import com.tuti.member.service.request.UpdateMyPageRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private ApplyMatchingStatus applyMatchingStatus;
    private String matchingDescription;

    @Embedded
    private Profile profile;

    @Embedded
    private AttachedJobTags attachedJobTags;

    @Embedded
    private AttachedSkillTags attachedSkillTags;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private AvailableDays availableDays;
    private String availableHours;

    private String password;
    private String name;
    private String birthYear;
    private String birthDay;

    private String businessNumber;

    @Builder
    public Member(Email email, Gender gender, ApplyMatchingStatus applyMatchingStatus, String matchingDescription, Profile profile, AttachedJobTags attachedJobTags, AttachedSkillTags attachedSkillTags, Role role, AvailableDays availableDays, String availableHours, String password, String name, String birthYear, String birthDay, String businessNumber) {
        this.email = email;
        this.gender = gender;
        this.applyMatchingStatus = applyMatchingStatus;
        this.matchingDescription = matchingDescription;
        this.profile = profile;
        this.attachedJobTags = attachedJobTags;
        this.attachedSkillTags = attachedSkillTags;
        this.role = role;
        this.availableDays = availableDays;
        this.availableHours = availableHours;
        this.password = password;
        this.name = name;
        this.birthYear = birthYear;
        this.birthDay = birthDay;
        this.businessNumber = businessNumber;
    }

    public void update(UpdateMyPageRequest updateMyPageRequest) {
        profile.update(updateMyPageRequest);
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
