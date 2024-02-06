package com.tuti.payment.service.exception;

import com.tuti.common.exception.BadRequestException;

public class PaymentsNotFoundException extends BadRequestException {
    public PaymentsNotFoundException() {
        super("주문 정보를 찾을 수 없습니다.");
    }
}
