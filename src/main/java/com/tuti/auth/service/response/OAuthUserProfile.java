package com.tuti.auth.service.response;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.vo.Email;
import com.tuti.member.domain.vo.Gender;
import com.tuti.member.domain.vo.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
public class OAuthUserProfile {
    private String email;
    private String name;
    private String gender;
    private String birthYear;
    private String birthDay;

    @Builder
    public OAuthUserProfile(String email, String name, String gender , String birthYear, String birthDay) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.birthDay = birthDay;
    }

    public Member toMember() {
        return Member.builder()
                .email(new Email(email))
                .gender(Gender.of(gender))
                .name(name)
                .password(UUID.randomUUID().toString().substring(0, 15))
                .birthYear(birthYear)
                .birthDay(birthDay)
                .role(Role.STUDENT)
                .build();
    }

}
