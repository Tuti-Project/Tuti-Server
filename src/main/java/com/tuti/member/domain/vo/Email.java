package com.tuti.member.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Email {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    @Column(name = "email", unique = true, nullable = false)
    private String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (Objects.isNull(value)) {
            throw new NullPointerException("이메일은 null일 수 없습니다.");
        }
        isFormatValid(value);
    }

    private void isFormatValid(String value) {
        if (!value.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("올바르지 않은 이메일 형식입니다.");
        }
    }
}
