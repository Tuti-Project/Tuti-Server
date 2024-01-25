package com.tuti.member.service.response;


import com.tuti.member.domain.vo.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProfileResponse {
    private String major;
    private String university;
    private String region;

    public ProfileResponse(Profile profile) {
        this.major = profile.getMajor();
        this.university = profile.getUniversity();
        this.region = profile.getRegion();
    }
}
