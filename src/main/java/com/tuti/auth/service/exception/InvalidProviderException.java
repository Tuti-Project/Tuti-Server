package com.tuti.auth.service.exception;

import com.tuti.common.exception.BadRequestException;

public class InvalidProviderException extends BadRequestException {
    public InvalidProviderException() {
        super("provider가 올바르지 않습니다. naver 혹은 kakao만 입력해주세요.");
    }
}
