package com.tuti.member.domain.exception;

import com.tuti.common.exception.BadRequestException;

public class InvalidDayOfWeekException extends BadRequestException {
    public InvalidDayOfWeekException() {
        super("요일 값이 올바르지 않습니다.");
    }
}
