package com.tuti.member.domain;

import com.tuti.member.domain.vo.Email;
import com.tuti.member.domain.vo.Gender;
import com.tuti.member.domain.vo.Profile;
import com.tuti.member.domain.vo.Role;
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

    @Embedded
    private Email email;

    @Enumerated
    private Gender gender;

    @Embedded
    private Profile profile;

    private String password;
    private String name;
    private String birthYear;
    private String birthDay;
    private Role role;
    private String businessNumber;

    @Builder
    public Member(Email email, Gender gender, String password, String name, String birthYear, String birthDay, Role role, String businessNumber) {
        this.email = email;
        this.gender = gender;
        this.profile = Profile.create();
        this.password = password;
        this.name = name;
        this.birthYear = birthYear;
        this.birthDay = birthDay;
        this.role = role;
        this.businessNumber = businessNumber;
    }
}
