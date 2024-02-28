package com.tuti.log.repository;

import com.tuti.log.domain.JoinCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinCountRepository extends JpaRepository<JoinCount, Long> {
}
