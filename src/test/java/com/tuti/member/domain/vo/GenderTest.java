package com.tuti.member.domain.vo;


import com.tuti.member.domain.exception.InvalidGenderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class GenderTest {

    @DisplayName("FEMALE을 생성한다")
    @ParameterizedTest
    @ValueSource(strings = {"female", "F"})
    void createFemaleGender(String value) {
        // when
        Gender female = Gender.of(value);

        // then
        assertThat(female).isEqualTo(Gender.FEMALE);
    }

    @DisplayName("MALE을 생성한다")
    @ParameterizedTest
    @ValueSource(strings = {"male", "M"})
    void createMaleGender(String value) {
        // when
        Gender female = Gender.of(value);

        // then
        assertThat(female).isEqualTo(Gender.MALE);
    }

    @DisplayName("female, F, male, M 이외의 값을 입력한다")
    @ParameterizedTest
    @ValueSource(strings = {"남자", "여자", "f", "m", "FEMALE", "MALE"})
    void createFailGender(String value) {
        // when, then
        assertThatThrownBy(() -> Gender.of(value))
                .isInstanceOf(InvalidGenderException.class);
    }

}