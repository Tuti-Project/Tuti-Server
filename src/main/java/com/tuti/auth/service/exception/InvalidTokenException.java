package com.tuti.auth.service.exception;

import com.tuti.common.exception.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {
    public InvalidTokenException() {
        super("유효하지 않은 토큰입니다.");
    }
}
