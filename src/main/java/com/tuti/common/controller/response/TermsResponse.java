package com.tuti.common.controller.response;

import com.tuti.common.entity.terms_conditions.TermsConditions;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TermsResponse {
    private String termsOfUseService;
    private String privacyPolicy;
    private String RefundPolicy;

    public TermsResponse(TermsConditions termsConditions) {
        this.termsOfUseService = termsConditions.getTermsOfUseService();
        this.privacyPolicy = termsConditions.getPrivacyPolicy();
        RefundPolicy = termsConditions.getRefundPolicy();
    }
}
