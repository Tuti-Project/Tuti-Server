package com.tuti.member.service;

import com.tuti.common.RepositoryTest;
import com.tuti.fixtures.MemberFixtures;
import com.tuti.member.domain.Member;
import com.tuti.member.domain.repository.MemberRepository;
import com.tuti.member.domain.vo.*;
import com.tuti.member.service.MemberService;
import com.tuti.member.service.request.EnterpriseJoinRequest;
import com.tuti.member.service.request.StudentJoinRequest;
import com.tuti.member.service.request.UpdateMyPageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@RepositoryTest
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
        memberRepository.save(MemberFixtures.정우());
    }

    @DisplayName("일반 회원(학생)으로 가입한다")
    @Test
    void joinAsStudent() {
        // given
        StudentJoinRequest request = StudentJoinRequest.builder()
                .email("student@naver.com")
                .gender("M")
                .build();

        // when
        memberService.joinStudent(request);
        Member findMember = memberRepository.findByEmail(new Email("student@naver.com")).get();

        // then
        assertThat(findMember.getRole()).isEqualTo(Role.STUDENT);
    }

    @DisplayName("기업 회원으로 가입한다")
    @Test
    void joinAsEnterprise() {
        // given
        EnterpriseJoinRequest request = EnterpriseJoinRequest.builder()
                .email("student@naver.com")
                .gender("M")
                .build();

        // when
        memberService.joinEnterprise(request);
        Member findMember = memberRepository.findByEmail(new Email("student@naver.com")).get();

        // then
        assertThat(findMember.getRole()).isEqualTo(Role.ENTERPRISE);
    }

    @DisplayName("마이페이지 정보를 수정한다")
    @Test
    void updateMyPage() {
        // given
        Member member = memberRepository.findByEmail(MemberFixtures.정우_이메일).get();
        UpdateMyPageRequest request = new UpdateMyPageRequest("단국대학교", "소프트웨어학과", "image-uri", Set.of("IT/SW", "사무/행정"),
                Set.of("한글/워드", "엑셀"), "개발쪽 업무를 희망합니다", ApplyMatchingStatus.ON, "", Set.of("MON", "TUE", "WED"), "09:00 - 18:00");

        // when
        memberService.updateMyPage(member.getId(), request);

        // then
        assertThat(member.getProfile())
                .extracting("university", "major", "imageUrl", "description", "applyMatchingStatus", "matchingDescription", "availableHours", "attachedJobTags", "attachedSkillTags", "availableDays")
                .containsExactly("단국대학교", "소프트웨어학과", "image-uri", "개발쪽 업무를 희망합니다", ApplyMatchingStatus.ON, "", "09:00 - 18:00", new AttachedJobTags(Set.of(new AttachedJobTag("IT/SW"), new AttachedJobTag("사무/행정"))),
                        new AttachedSkillTags(Set.of(new AttachedSkillTag("한글/워드"), new AttachedSkillTag("엑셀"))), new AvailableDays(Set.of(DayOfWeek.MON, DayOfWeek.TUE, DayOfWeek.WED)));
    }

}
