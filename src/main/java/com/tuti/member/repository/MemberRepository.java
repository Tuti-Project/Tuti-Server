package com.tuti.member.repository;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.vo.Email;
import com.tuti.member.domain.vo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(Email email);

    @Query(value = "SELECT * FROM member m WHERE m.id < :memberId ORDER BY m.id DESC LIMIT :pageSize", nativeQuery = true)
    List<Member> findMemberWithPaging(@Param("memberId") Long memberId, @Param("pageSize") int pageSize);

    @Query("SELECT m FROM Member m JOIN FETCH m.profile mp " +
            "LEFT JOIN FETCH mp.attachedJobTags.attachedJobTags " +
            "LEFT JOIN FETCH mp.availableDays.availableDays " +
            "LEFT JOIN FETCH mp.attachedSkillTags.attachedSkillTags " +
            "WHERE m.id = :id")
    Optional<Member> findByIdFetch(@Param("id") Long id);

    Boolean existsByEmail(Email email);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.role = :role AND m.createdDate = CURRENT_DATE")
    Long countJoinedToday(Role role);
}
