package com.tuti.member.service;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.repository.MemberRepository;
import com.tuti.member.service.exception.MemberNotFoundException;
import com.tuti.member.service.response.MembersResponse;
import com.tuti.member.service.response.MyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SearchingMemberService {

    private final MemberRepository memberRepository;

    public MembersResponse getMembers(int page) {
        Slice<Member> members = memberRepository.findSliceBy(PageRequest.of(page, 10));
        return MembersResponse.builder()
                .members(members.getContent())
                .hasNext(members.hasNext())
                .build();
    }

    public MyPageResponse getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        return new MyPageResponse(member);
    }
}