package com.tuti.member.domain.vo;

import com.tuti.member.service.request.UpdateMyPageRequest;
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

    private String imageUrl;

    private String description;

    @Builder
    public Profile(String university, String major, String imageUrl, String description) {
        this.university = university;
        this.major = major;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public static Profile create() {
        return Profile.builder()
                .major(BLANK)
                .university(BLANK)
                .imageUrl(BLANK)
                .description(BLANK)
                .build();
    }

    public void update(UpdateMyPageRequest updateMyPageRequest) {
        this.university = updateMyPageRequest.getUniversity();
        this.major = updateMyPageRequest.getMajor();
        this.imageUrl = updateMyPageRequest.getImageUrl();
        this.description = updateMyPageRequest.getDescription();
    }

}
