package com.tuti.member.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Profile {
    private static final String BLANK = "";

    private String university;

    private String major;

    private String region;

    @Builder
    private Profile(String university, String major, String region) {
        this.university = university;
        this.major = major;
        this.region = region;
    }

    public static Profile create() {
        return Profile.builder()
                .major(BLANK)
                .university(BLANK)
                .region(BLANK)
                .build();
    }

}
