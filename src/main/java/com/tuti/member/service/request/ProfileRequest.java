package com.tuti.member.service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProfileRequest {
    private String major;
    private String university;
    private String region;
    private String imageUrl;
}
