package com.tuti.member.service;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.repository.MemberRepository;
import com.tuti.member.domain.vo.Email;
import com.tuti.member.domain.vo.Gender;
import com.tuti.member.domain.vo.Profile;
import com.tuti.member.domain.vo.Role;
import com.tuti.member.service.request.EnterpriseJoinRequest;
import com.tuti.member.service.request.StudentJoinRequest;
import com.tuti.member.service.request.ProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void joinStudent(StudentJoinRequest studentJoinRequest) {
        Member member = Member.builder()
                .email(new Email(studentJoinRequest.getEmail()))
                .name(studentJoinRequest.getName())
                .password(studentJoinRequest.getPassword())
                .birthYear(studentJoinRequest.getBirthYear())
                .birthDay(studentJoinRequest.getBirthDay())
                .gender(Gender.of(studentJoinRequest.getGender()))
                .role(Role.STUDENT)
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public void joinEnterprise(EnterpriseJoinRequest enterpriseJoinRequest) {
        Member member = Member.builder()
                .email(new Email(enterpriseJoinRequest.getEmail()))
                .name(enterpriseJoinRequest.getName())
                .password(enterpriseJoinRequest.getPassword())
                .birthYear(enterpriseJoinRequest.getBirthYear())
                .birthDay(enterpriseJoinRequest.getBirthDay())
                .gender(Gender.of(enterpriseJoinRequest.getGender()))
                .role(Role.ENTERPRISE)
                .businessNumber(enterpriseJoinRequest.getBusinessNumber())
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public void updateProfile(Long memberId, ProfileRequest profileRequest) {
        Profile profile = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new).getProfile();

        profile.update(profileRequest);
    }
}
