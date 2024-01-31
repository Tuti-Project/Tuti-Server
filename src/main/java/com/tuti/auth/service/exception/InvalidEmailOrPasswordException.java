package com.tuti.auth.service.exception;

import com.tuti.common.exception.BadRequestException;

public class InvalidEmailOrPasswordException extends BadRequestException {
    public InvalidEmailOrPasswordException() {
        super("존재하지 않는 이메일이거나 비밀번호가 올바르지 않습니다.");
    }
}
