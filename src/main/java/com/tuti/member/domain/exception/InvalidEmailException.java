package com.tuti.member.domain.exception;

import com.tuti.common.exception.BadRequestException;

public class InvalidEmailException extends BadRequestException {
    public InvalidEmailException() {
        super("이메일 형식이 올바르지 않거나, 값이 null 입니다.");
    }
}
