package com.tuti.member.service;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.repository.MemberRepository;
import com.tuti.member.service.exception.MemberNotFoundException;
import com.tuti.member.service.response.MemberDetailResponse;
import com.tuti.member.service.response.MembersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SearchingMemberService {

    private final MemberRepository memberRepository;

    public MembersResponse getMembers(Long lastMemberId) {
        int pageSize = 10;
        lastMemberId = isFirstPage(lastMemberId) ? Long.MAX_VALUE : lastMemberId;

        List<Member> members = memberRepository.findMemberWithPaging(lastMemberId, pageSize + 1);
        boolean hasNext = false;

        if (!isLastPage(members.size(), pageSize)) {
            members.remove(pageSize);
            hasNext = true;
        }

        return MembersResponse.builder()
                .last(members.get(members.size() - 1).getId())
                .members(members)
                .hasNext(hasNext)
                .build();
    }

    public MemberDetailResponse getMember(Long memberId) {
        Member member = memberRepository.findByIdFetch(memberId)
                .orElseThrow(MemberNotFoundException::new);

        return new MemberDetailResponse(member);
    }

    private boolean isLastPage(int memberSize, int pageSize) {
        return (memberSize < pageSize) ? true : false;
    }

    private boolean isFirstPage(Long lastMemberId) {
        return (lastMemberId == 0) ? true : false;
    }

}
