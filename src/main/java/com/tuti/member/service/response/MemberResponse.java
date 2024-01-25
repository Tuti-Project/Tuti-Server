package com.tuti.member.service.response;

import com.tuti.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberResponse {
    private String name;
    private String university;
    private String major;

    public MemberResponse(Member member) {
        this.name = member.getName();
        this.university = member.getProfile().getUniversity();
        this.major = member.getProfile().getMajor();
    }
}
