package com.tuti.member.domain.vo;

import java.util.Arrays;
import java.util.List;

public enum Gender {
    FEMALE(List.of("female, F")), MALE(List.of("male", "M"));

    private final List<String> names;

    Gender(List<String> names) {
        this.names = names;
    }

    public static Gender of(String value) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.names.contains(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }

}
