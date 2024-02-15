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

        lastMemberId = getLastMemberId(lastMemberId);

        List<Member> members = memberRepository.findMemberWithPaging(lastMemberId, pageSize + 1);

        return MembersResponse.builder()
                .last(members.get(members.size() - 1).getId())
                .members(members)
                .hasNext(isHasNext(members, pageSize)).build();
    }

    public MemberDetailResponse getMember(Long memberId) {
        Member member = memberRepository.findByIdFetch(memberId)
                .orElseThrow(MemberNotFoundException::new);

        return new MemberDetailResponse(member);
    }

    private boolean isHasNext(List<Member> members, int pageSize) {
        if (members.size() > pageSize) {
            members.remove(pageSize);
            return true;
        }
        return false;
    }

    private Long getLastMemberId(Long lastMemberId) {
        return (lastMemberId == 0) ? Long.MAX_VALUE : lastMemberId;
    }

}
