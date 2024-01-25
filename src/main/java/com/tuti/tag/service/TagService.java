package com.tuti.tag.service;

import com.tuti.tag.domain.JobTag;
import com.tuti.tag.domain.SkillTag;
import com.tuti.tag.domain.repository.JobTagRepository;
import com.tuti.tag.domain.repository.SkillTagRepository;
import com.tuti.tag.service.response.JobTagResponse;
import com.tuti.tag.service.response.SkillTagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final JobTagRepository jobTagRepository;
    private final SkillTagRepository skillTagRepository;

    public JobTagResponse findAllJobTags() {
        List<String> jobTags = jobTagRepository.findAll().stream().map(JobTag::getName).collect(Collectors.toList());
        return new JobTagResponse(jobTags);
    }

    public SkillTagResponse findAllSkillTags() {
        List<String> skillTags = skillTagRepository.findAll().stream().map(SkillTag::getName).collect(Collectors.toList());
        return new SkillTagResponse(skillTags);
    }

}
