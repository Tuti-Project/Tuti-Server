package com.tuti.member.service;

import com.tuti.member.domain.Member;
import com.tuti.member.repository.MemberRepository;
import com.tuti.member.service.exception.MemberNotFoundException;
import com.tuti.member.service.response.MemberDetailResponse;
import com.tuti.member.service.response.MembersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SearchingMemberService {

    private final MemberRepository memberRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public MembersResponse getMembers(Long lastMemberId) {
        int pageSize = 10;

        lastMemberId = getLastMemberId(lastMemberId);

        List<Member> members = memberRepository.findMemberWithPaging(lastMemberId, pageSize + 1);

        return MembersResponse.builder()
                .last(members.get(members.size() - 1).getId())
                .members(members)
                .hasNext(isHasNext(members, pageSize)).build();
    }

    public MemberDetailResponse getMyPage(Long memberId) {
        Member findMember = memberRepository.findByIdFetch(memberId)
                .orElseThrow(MemberNotFoundException::new);

        return new MemberDetailResponse(findMember);
    }

    public MemberDetailResponse getMember(Long loginMemberId, Long findMemberId) {
        Member findMember = memberRepository.findByIdFetch(findMemberId)
                .orElseThrow(MemberNotFoundException::new);

        recordViewCount(loginMemberId, findMemberId);

        return new MemberDetailResponse(findMember);
    }

    public void recordViewCount(Long loginMemberId, Long findMemberId) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String value = findMemberId + "&" + loginMemberId;
        if (!operations.getOperations().hasKey(value)) {
            operations.set(value, "");
        }
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
