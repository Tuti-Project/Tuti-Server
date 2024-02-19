package com.tuti.member.service.exception;

import com.tuti.common.exception.BadRequestException;

public class DuplicatedEmailException extends BadRequestException {
    public DuplicatedEmailException() {
        super("존재하는 이메일입니다.");
    }
}
