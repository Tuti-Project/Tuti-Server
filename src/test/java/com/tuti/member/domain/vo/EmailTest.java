package com.tuti.member.domain.vo;

import com.tuti.member.domain.exception.InvalidEmailException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @DisplayName("Email을 생성한다")
    @ParameterizedTest
    @ValueSource(strings = {"example@naver.com", "test@daum.net", "tuti@google.com"})
    void createEmail(String value) {
        // when
        Email email = new Email(value);

        // then
        Assertions.assertThat(email).isNotNull();
    }

    @DisplayName("올바르지 않은 이메일 형식을 입력한다")
    @ParameterizedTest
    @ValueSource(strings = {"example@@@navercom", "examplenavercom", "testa*ac.kr", "tuti@google!.com", "abc@abc"})
    void invalidEmailFormat(String value) {
        // when, then
        Assertions.assertThatThrownBy(() -> new Email(value))
                .isInstanceOf(InvalidEmailException.class);
    }

}