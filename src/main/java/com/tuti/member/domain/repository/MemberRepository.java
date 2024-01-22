package com.tuti.member.domain.repository;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(Email email);
}
