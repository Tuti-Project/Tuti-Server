package com.tuti.payment.service.exception;

import com.tuti.common.exception.BadRequestException;

public class PaymentsFailException extends BadRequestException {
    public PaymentsFailException() {
        super("결제가 정상적으로 완료되지 않았습니다.");
    }
}
