package com.tuti.member.domain;

import com.tuti.common.entity.BaseEntity;
import com.tuti.member.domain.vo.*;
import com.tuti.member.service.request.UpdateMyPageRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Embedded
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;
    private String name;
    private String birthYear;
    private String birthDay;
    private String businessNumber;

    @Builder
    public Member(Email email, Gender gender, Profile profile, Role role, String password, String name, String birthYear, String birthDay, String businessNumber) {
        this.email = email;
        this.gender = gender;
        this.profile = profile;
        this.role = role;
        this.password = password;
        this.name = name;
        this.birthYear = birthYear;
        this.birthDay = birthDay;
        this.businessNumber = businessNumber;
    }

}
