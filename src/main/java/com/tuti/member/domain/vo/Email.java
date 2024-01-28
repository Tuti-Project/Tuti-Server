package com.tuti.member.domain.vo;

import com.tuti.member.domain.exception.InvalidEmailException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Email {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@{1}[a-zA-Z0-9-]+\\.[a-zA-Z0-9]+$";

    @Column(name = "email", unique = true, nullable = false)
    private String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!isValidFormat(value)) {
            throw new InvalidEmailException();
        }
    }

    private boolean isValidFormat(String value) {
        return value.matches(EMAIL_REGEX);
    }
}
