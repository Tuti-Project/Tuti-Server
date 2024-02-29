package com.tuti.log.service;

import com.tuti.log.domain.JoinCount;
import com.tuti.log.domain.Visitor;
import com.tuti.log.repository.JoinCountRepository;
import com.tuti.log.repository.VisitorRepository;
import com.tuti.member.domain.vo.Role;
import com.tuti.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatisticsLoggingService {

    private final RedisTemplate<String, String> redisTemplate;

    private final VisitorRepository visitorRepository;
    private final MemberRepository memberRepository;
    private final JoinCountRepository joinCountRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateNumberOfVisitorPerDate() {
        Set<String> keys = redisTemplate.keys("*_*");

        for (String key : keys) {
            String[] parts = key.split("_");
            String userIp = parts[0];
            LocalDate date = LocalDate.parse(parts[1]);

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String userAgent = valueOperations.get(key);

            if(!visitorRepository.existsByUserIpAndDate(userIp, date)){
                Visitor visitor = Visitor.builder()
                        .userAgent(userAgent)
                        .userIp(userIp)
                        .date(date)
                        .build();

                visitorRepository.save(visitor);
            }

            redisTemplate.delete(key);
        }
    }

    @Scheduled(cron = "0 59 23 * * *")
    @Transactional
    public void updateNumberOfJoinPerDate() {
        Long student = memberRepository.countJoinedToday(Role.STUDENT);
        Long enterprise = memberRepository.countJoinedToday(Role.ENTERPRISE);

        joinCountRepository.save(JoinCount.builder()
                .student(student)
                .enterprise(enterprise)
                .build());
    }

}
