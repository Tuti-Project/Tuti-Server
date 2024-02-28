package com.tuti.log.repository;

import com.tuti.log.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    boolean existsByUserIpAndDate(String userIp, LocalDate date);
}
