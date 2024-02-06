package com.tuti.payment.domain;

import com.tuti.payment.domain.vo.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Payments {

    @Id @GeneratedValue
    private Long id;
    private Long price;
    private PaymentStatus status;
    private String paymentUid;
    private String orderUid;

    @Builder
    public Payments(String orderUid, Long price, PaymentStatus status) {
        this.orderUid = orderUid;
        this.price = price;
        this.status = status;
    }

    public void processSuccess(String paymentUid) {
        this.paymentUid = paymentUid;
        changeStatus(PaymentStatus.OK);
    }

    public void changeStatus(PaymentStatus status) {
        this.status = status;
    }
}