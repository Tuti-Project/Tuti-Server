package com.tuti.member.service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class EnterpriseJoinRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String birthYear;

    @NotBlank
    private String birthDay;

    @NotBlank
    private String gender;

    @NotBlank
    private String businessNumber;
}
