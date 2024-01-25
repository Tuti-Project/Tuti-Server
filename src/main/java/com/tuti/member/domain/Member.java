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

    @Embedded
    private Profile profile;

    @Embedded
    private AttachedJobTags attachedJobTags;

    @Embedded
    private AttachedSkillTags attachedSkillTags;

    private String password;
    private String name;
    private String birthYear;
    private String birthDay;
    private Role role;
    private String businessNumber;

    @Builder
    public Member(Email email, Gender gender, String password, String name, String birthYear, String birthDay, Role role, String businessNumber, ApplyMatchingStatus applyMatchingStatus) {
        this.email = email;
        this.gender = gender;
        this.applyMatchingStatus = applyMatchingStatus;
        this.profile = Profile.create();
        this.password = password;
        this.name = name;
        this.birthYear = birthYear;
        this.birthDay = birthDay;
        this.role = role;
        this.businessNumber = businessNumber;
    }

    public void update(UpdateMyPageRequest updateMyPageRequest) {
        profile.update(updateMyPageRequest);
        this.applyMatchingStatus = updateMyPageRequest.getApplyMatchingStatus();
        this.attachedJobTags = new AttachedJobTags(updateMyPageRequest.getJobTags().stream()
                .map(AttachedJobTag::new)
                .collect(Collectors.toList()));
        this.attachedSkillTags = new AttachedSkillTags(updateMyPageRequest.getSkillTags().stream()
                .map(AttachedSkillTag::new)
                .collect(Collectors.toList()));
    }

}
