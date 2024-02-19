package com.tuti.member.service;

import com.tuti.member.domain.Member;
import com.tuti.member.repository.MemberRepository;
import com.tuti.member.domain.vo.*;
import com.tuti.member.service.exception.DuplicatedEmailException;
import com.tuti.member.service.exception.MemberNotFoundException;
import com.tuti.member.service.request.EnterpriseJoinRequest;
import com.tuti.member.service.request.UpdateMyPageRequest;
import com.tuti.member.service.request.StudentJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tuti.member.domain.vo.Profile.BLANK;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void joinStudent(StudentJoinRequest studentJoinRequest) {
        Email email = new Email(studentJoinRequest.getEmail());
        validateDuplicateEmail(email);
        memberRepository.save(Member.builder()
                .email(email).name(studentJoinRequest.getName()).password(studentJoinRequest.getPassword())
                .birthYear(studentJoinRequest.getBirthYear()).birthDay(studentJoinRequest.getBirthDay()).gender(Gender.of(studentJoinRequest.getGender())).role(Role.STUDENT)
                .profile(Profile.create()).businessNumber(BLANK)
                .build());
    }

    public void joinEnterprise(EnterpriseJoinRequest enterpriseJoinRequest) {
        Email email = new Email(enterpriseJoinRequest.getEmail());
        validateDuplicateEmail(email);
        memberRepository.save(Member.builder()
                .email(new Email(enterpriseJoinRequest.getEmail())).name(enterpriseJoinRequest.getName()).password(enterpriseJoinRequest.getPassword())
                .birthYear(enterpriseJoinRequest.getBirthYear()).birthDay(enterpriseJoinRequest.getBirthDay()).gender(Gender.of(enterpriseJoinRequest.getGender()))
                .role(Role.ENTERPRISE).businessNumber(enterpriseJoinRequest.getBusinessNumber())
                .build());
    }

    public void updateMyPage(Long memberId, UpdateMyPageRequest updateMyPageRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.updateProfile(updateMyPageRequest);
    }

    private void validateDuplicateEmail(Email email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

}
