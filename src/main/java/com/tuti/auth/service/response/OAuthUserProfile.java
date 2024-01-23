package com.tuti.auth.service.response;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.vo.Email;
import com.tuti.member.domain.vo.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class OAuthUserProfile {
    private String email;
    private String name;
    private String gender;
    private String phoneNumber;
    private String birthYear;
    private String birthDay;

    @Builder
    public OAuthUserProfile(String email, String name, String gender, String phoneNumber, String birthYear, String birthDay) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthYear = birthYear;
        this.birthDay = birthDay;
    }

    public Member toMember() {
        return Member.builder()
                .email(new Email(email))
                .gender(Gender.of(gender))
                .name(name)
                .birthYear(birthYear)
                .birthDay(birthDay)
                .build();
    }

}
