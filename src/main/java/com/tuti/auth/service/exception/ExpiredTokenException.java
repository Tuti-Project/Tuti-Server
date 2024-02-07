package com.tuti.auth.service.exception;

import com.tuti.common.exception.UnauthorizedException;

public class ExpiredTokenException extends UnauthorizedException {
    public ExpiredTokenException() {
        super("만료된 토큰입니다.");
    }
}
