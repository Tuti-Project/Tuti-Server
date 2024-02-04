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
public class AttachedSkillTags {

    @ElementCollection
    @CollectionTable(name = "member_skill_tag", joinColumns = @JoinColumn(name = "member_id"))
    private Set<AttachedSkillTag> attachedSkillTags = new HashSet<>();

    public Set<AttachedSkillTag> get() {
        return new HashSet<>(attachedSkillTags);
    }

    public static AttachedSkillTags empty() {
        return new AttachedSkillTags(Set.of());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttachedSkillTags that = (AttachedSkillTags) o;
        return Objects.equals(get(), that.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachedSkillTags);
    }

}
