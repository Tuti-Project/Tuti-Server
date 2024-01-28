package com.tuti.member.domain.exception;

import com.tuti.common.exception.InvalidFormatException;

public class InvalidDayOfWeekException extends InvalidFormatException {
    public InvalidDayOfWeekException() {
        super("요일 값이 올바르지 않습니다.");
    }
}
