package com.tuti.member.domain.repository;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.vo.Email;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(Email email);

    Slice<Member> findSliceBy(Pageable pageable);

    @Query("SELECT m FROM Member m JOIN FETCH m.profile mp " +
            "LEFT JOIN FETCH mp.attachedJobTags.attachedJobTags " +
            "LEFT JOIN FETCH mp.availableDays.availableDays " +
            "LEFT JOIN FETCH mp.attachedSkillTags.attachedSkillTags " +
            "WHERE m.id = :id")
    Optional<Member> findByIdFetch(@Param("id") Long id);

}
