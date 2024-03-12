package com.tuti.common.entity.terms_conditions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TermsConditions {

    @Id @GeneratedValue
    private Long id;
    @Column(length = 1500)
    private String termsOfUseService;
    @Column(length = 1500)
    private String privacyPolicy;
    @Column(length = 1500)
    private String RefundPolicy;

    @Builder
    public TermsConditions(String termsOfUseService, String privacyPolicy, String refundPolicy) {
        this.termsOfUseService = termsOfUseService;
        this.privacyPolicy = privacyPolicy;
        RefundPolicy = refundPolicy;
    }
}
