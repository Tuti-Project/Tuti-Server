package com.tuti.member.domain.vo;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachedJobTags {

    @ElementCollection
    @CollectionTable(name = "member_job_tag", joinColumns = @JoinColumn(name = "member_id"))
    private Set<AttachedJobTag> attachedJobTags = new HashSet<>();

    public Set<AttachedJobTag> get() {
        return new HashSet<>(attachedJobTags);
    }

    public static AttachedJobTags empty() {
        return new AttachedJobTags(Set.of());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttachedJobTags that = (AttachedJobTags) o;
        return Objects.equals(get(), that.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachedJobTags);
    }

}
