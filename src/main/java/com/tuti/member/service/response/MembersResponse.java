package com.tuti.member.service.response;

import com.tuti.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class MembersResponse {
    private List<MemberSimpleResponse> members;
    private boolean hasNext;
    private Long last;

    @Builder
    public MembersResponse(List<Member> members, boolean hasNext, Long last) {
        this.members = members.stream().map(m -> new MemberSimpleResponse(m)).collect(Collectors.toList());
        this.hasNext = hasNext;
        this.last = last;
    }
}
