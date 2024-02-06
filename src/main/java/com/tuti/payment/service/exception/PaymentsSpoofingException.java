package com.tuti.payment.service.exception;

import com.tuti.common.exception.BadRequestException;

public class PaymentsSpoofingException extends BadRequestException {
    public PaymentsSpoofingException() {
        super("결제한 금액이 변조되었습니다.");
    }
}
