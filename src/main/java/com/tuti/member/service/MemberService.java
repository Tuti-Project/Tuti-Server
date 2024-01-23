package com.tuti.member.service;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.repository.MemberRepository;
import com.tuti.member.domain.vo.Email;
import com.tuti.member.domain.vo.Gender;
import com.tuti.member.service.request.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void join(JoinRequest joinRequest) {
        Member member = Member.builder()
                .email(new Email(joinRequest.getEmail()))
                .name(joinRequest.getName())
                .birthYear(joinRequest.getBirthYear())
                .birthDay(joinRequest.getBirthDay())
                .gender(Gender.of(joinRequest.getGender()))
                .build();

        memberRepository.save(member);
    }
}
