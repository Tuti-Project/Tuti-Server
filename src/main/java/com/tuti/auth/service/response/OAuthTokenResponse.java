package com.tuti.auth.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class OAuthTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @Builder
    public OAuthTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
