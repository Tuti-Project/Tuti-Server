package com.tuti.tag.repository;

import com.tuti.tag.domain.JobTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTagRepository extends JpaRepository<JobTag, Long> {
}
