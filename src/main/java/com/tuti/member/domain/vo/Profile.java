package com.tuti.member.domain.vo;

import com.tuti.member.service.request.ProfileRequest;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Profile {
    public static final String BLANK = "";

    private String university;

    private String major;

    private String region;

    private String imageUrl;

    @Builder
    public Profile(String university, String major, String region, String imageUrl) {
        this.university = university;
        this.major = major;
        this.region = region;
        this.imageUrl = imageUrl;
    }

    public static Profile create() {
        return Profile.builder()
                .major(BLANK)
                .university(BLANK)
                .region(BLANK)
                .imageUrl(BLANK)
                .build();
    }

    public void update(ProfileRequest profileRequest) {
        this.university = profileRequest.getUniversity();
        this.major = profileRequest.getMajor();
        this.region = profileRequest.getRegion();
        this.imageUrl = profileRequest.getImageUrl();
    }

}
