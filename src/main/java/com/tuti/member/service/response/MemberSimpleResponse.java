package com.tuti.member.service.response;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.vo.ApplyMatchingStatus;
import com.tuti.member.domain.vo.AttachedJobTag;
import com.tuti.member.domain.vo.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberSimpleResponse {
    private Long memberId;
    private String name;
    private String university;
    private String major;
    private String imageUrl;
    private ApplyMatchingStatus applyMatchingStatus;
    private Set<String> jobTags;

    public MemberSimpleResponse(Member member) {
        Profile profile = member.getProfile();
        this.memberId = member.getId();
        this.name = member.getName();
        this.university = profile.getUniversity();
        this.major = profile.getMajor();
        this.imageUrl = profile.getImageUrl();
        this.applyMatchingStatus = profile.getApplyMatchingStatus();
        this.jobTags = profile.getAttachedJobTags().get().stream().map(AttachedJobTag::getJobTagName).collect(Collectors.toSet());
    }
}
