package com.tuti.member.domain.vo;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachedSkillTags {

    @ElementCollection
    @CollectionTable(name = "member_skill_tag", joinColumns = @JoinColumn(name = "member_id"))
    private List<AttachedSkillTag> attachedSkillTags = new ArrayList<>();

    public List<AttachedSkillTag> get() {
        return new ArrayList<>(attachedSkillTags);
    }

    public static AttachedSkillTags empty() {
        return new AttachedSkillTags(List.of());
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
