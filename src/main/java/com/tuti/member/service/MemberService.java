package com.tuti.member.service;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.repository.MemberRepository;
import com.tuti.member.domain.vo.*;
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
        memberRepository.save(Member.createStudentMember(studentJoinRequest));
    }

    public void joinEnterprise(EnterpriseJoinRequest enterpriseJoinRequest) {
        memberRepository.save(Member.createEnterpriseMember(enterpriseJoinRequest));
    }

    public void updateMyPage(Long memberId, UpdateMyPageRequest updateMyPageRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.updateProfile(updateMyPageRequest);
    }
}
