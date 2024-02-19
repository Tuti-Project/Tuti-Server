package com.tuti.member.domain.exception;

import com.tuti.common.exception.BadRequestException;

public class InvalidGenderException extends BadRequestException {
    public InvalidGenderException() {
        super("성별 값이 올바르지 않습니다. female, F 혹은 male, M만 입력해주세요.");
    }
}
