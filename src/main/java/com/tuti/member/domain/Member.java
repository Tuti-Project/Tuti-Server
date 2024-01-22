package com.tuti.member.domain;

import com.tuti.member.domain.vo.Email;
import com.tuti.member.domain.vo.Gender;
import com.tuti.member.domain.vo.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    @Embedded
    private Email email;

    @Enumerated
    private Gender gender;

    @Embedded
    private Profile profile;

    private String name;
    private String birthYear;
    private String birthDay;

    @Builder
    public Member(String loginId, Email email, Gender gender, String name, String birthYear, String birthDay, Profile profile) {
        this.loginId = loginId;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.birthYear = birthYear;
        this.birthDay = birthDay;
        this.profile = profile;
    }

}
