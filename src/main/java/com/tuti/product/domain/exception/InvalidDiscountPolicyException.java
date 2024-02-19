package com.tuti.product.domain.exception;

import com.tuti.common.exception.BadRequestException;

public class InvalidDiscountPolicyException extends BadRequestException {
    public InvalidDiscountPolicyException() {
        super("할인 정책이 올바르지 않습니다.");
    }
}
