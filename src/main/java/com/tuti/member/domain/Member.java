package com.tuti.member.domain;

import com.tuti.common.entity.BaseEntity;
import com.tuti.member.domain.vo.*;
import com.tuti.member.service.request.EnterpriseJoinRequest;
import com.tuti.member.service.request.StudentJoinRequest;
import com.tuti.member.service.request.UpdateMyPageRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.tuti.member.domain.vo.Profile.BLANK;

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

    public static Member createStudentMember(StudentJoinRequest studentJoinRequest) {
        return Member.builder()
                .email(new Email(studentJoinRequest.getEmail()))
                .name(studentJoinRequest.getName())
                .password(studentJoinRequest.getPassword())
                .birthYear(studentJoinRequest.getBirthYear())
                .birthDay(studentJoinRequest.getBirthDay())
                .gender(Gender.of(studentJoinRequest.getGender()))
                .role(Role.STUDENT)
                .profile(Profile.create())
                .businessNumber(BLANK)
                .build();
    }

    public static Member createEnterpriseMember(EnterpriseJoinRequest enterpriseJoinRequest) {
        return Member.builder()
                .email(new Email(enterpriseJoinRequest.getEmail()))
                .name(enterpriseJoinRequest.getName())
                .password(enterpriseJoinRequest.getPassword())
                .birthYear(enterpriseJoinRequest.getBirthYear())
                .birthDay(enterpriseJoinRequest.getBirthDay())
                .gender(Gender.of(enterpriseJoinRequest.getGender()))
                .role(Role.ENTERPRISE)
                .businessNumber(enterpriseJoinRequest.getBusinessNumber())
                .build();
    }

    public void updateProfile(UpdateMyPageRequest updateMyPageRequest) {
        profile.update(updateMyPageRequest);
    }

}
