package com.tuti.member.domain.vo;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvailableDays {

    @ElementCollection
    @CollectionTable(name = "member_available_days", joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    Set<DayOfWeek> availableDays = new HashSet<>();

    public Set<DayOfWeek> get() {
        return new HashSet<>(availableDays);
    }

    public static AvailableDays empty() {
        return new AvailableDays(Set.of());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AvailableDays that = (AvailableDays) o;
        return Objects.equals(get(), that.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(availableDays);
    }

}
