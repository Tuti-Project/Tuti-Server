package com.tuti.member.domain.vo;

import com.tuti.member.domain.exception.InvalidDayOfWeekException;

import java.util.Arrays;

public enum DayOfWeek {
    MON("MON"),
    TUE("TUE"),
    WED("WED"),
    THU("THU"),
    FRI("FRI"),
    SAT("SAT"),
    SUN("SUN");

    private String dayName;

    DayOfWeek(String dayName) {
        this.dayName = dayName;
    }

    public static DayOfWeek of(String value) {
        return Arrays.stream(DayOfWeek.values())
                .filter(day -> day.dayName.equals(value))
                .findFirst()
                .orElseThrow(InvalidDayOfWeekException::new);
    }

    public String getDayName() {
        return dayName;
    }

}