package com.tuti.member.service;

import com.tuti.common.RepositoryTest;
import com.tuti.fixtures.MemberFixtures;
import com.tuti.fixtures.ProfileFixtures;
import com.tuti.member.domain.Member;
import com.tuti.member.repository.MemberRepository;
import com.tuti.member.domain.vo.*;
import com.tuti.member.service.response.MemberDetailResponse;
import com.tuti.member.service.response.MembersResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
public class SearchingMemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private SearchingMemberService searchingMemberService;

    @BeforeEach
    void setUp() {
        searchingMemberService = new SearchingMemberService(memberRepository, redisTemplate);

        memberRepository.save(MemberFixtures.정우());
        memberRepository.save(MemberFixtures.혜린());
        memberRepository.save(MemberFixtures.심규());
        memberRepository.save(MemberFixtures.주영());
        memberRepository.save(MemberFixtures.창환());
    }

    @DisplayName("사용자의 상세 프로필 페이지 정보를 조회한다")
    @Test
    void findMemberDetail() {
        // given
        Member member = memberRepository.findByEmail(MemberFixtures.정우_이메일).get();
        Profile profile = member.getProfile();

        // when
        MemberDetailResponse response = searchingMemberService.getMyPage(member.getId());

        // then
        assertAll(
                () -> assertThat(response.getMemberId()).isEqualTo(member.getId()),
                () -> assertThat(response.getName()).isEqualTo(member.getName()),
                () -> assertThat(response.getAge()).isEqualTo(calculateAge(member.getBirthYear())),
                () -> assertThat(response.getGender()).isEqualTo(genderToString(member.getGender())),
                () -> assertThat(response.getUniversity()).isEqualTo(profile.getUniversity()),
                () -> assertThat(response.getMajor()).isEqualTo(profile.getMajor()),
                () -> assertThat(response.getDescription()).isEqualTo(profile.getDescription()),
                () -> assertThat(response.getJobTags()).isEqualTo(jobTagsToStringSet(profile.getAttachedJobTags().get())),
                () -> assertThat(response.getSkillTags()).isEqualTo(skillTagsToStringSet(profile.getAttachedSkillTags().get())),
                () -> assertThat(response.getApplyMatchingStatus()).isEqualTo(profile.getApplyMatchingStatus()),
                () -> assertThat(response.getMatchingDescription()).isEqualTo(profile.getMatchingDescription()),
                () -> assertThat(response.getAvailableDays()).isEqualTo(availableDaysToStringSet(profile.getAvailableDays().get())),
                () -> assertThat(response.getAvailableHours()).isEqualTo(profile.getAvailableHours()));
    }

    @DisplayName("모든 회원의 간단한 프로필 정보를 최대 10개씩 조회한다")
    @Test
    void findMembersWithPaging() {
        // when
        MembersResponse response = searchingMemberService.getMembers(0L); // 인자는 페이지 값

        // then
        assertThat(response.getMembers())
                .extracting("name", "university", "major", "imageUrl", "jobTags", "applyMatchingStatus")
                .containsExactly(
                        tuple(MemberFixtures.창환_이름, ProfileFixtures.창환_대학, ProfileFixtures.창환_전공, ProfileFixtures.창환_이미지_주소, jobTagsToStringSet(ProfileFixtures.창환_직무_태그.get()), ProfileFixtures.창환_매칭_신청_여부),
                        tuple(MemberFixtures.주영_이름, ProfileFixtures.주영_대학, ProfileFixtures.주영_전공, ProfileFixtures.주영_이미지_주소, jobTagsToStringSet(ProfileFixtures.주영_직무_태그.get()), ProfileFixtures.주영_매칭_신청_여부),
                        tuple(MemberFixtures.심규_이름, ProfileFixtures.심규_대학, ProfileFixtures.심규_전공, ProfileFixtures.심규_이미지_주소, jobTagsToStringSet(ProfileFixtures.심규_직무_태그.get()), ProfileFixtures.심규_매칭_신청_여부),
                        tuple(MemberFixtures.혜린_이름, ProfileFixtures.혜린_대학, ProfileFixtures.혜린_전공, ProfileFixtures.혜린_이미지_주소, jobTagsToStringSet(ProfileFixtures.혜린_직무_태그.get()), ProfileFixtures.혜린_매칭_신청_여부),
                        tuple(MemberFixtures.정우_이름, ProfileFixtures.정우_대학, ProfileFixtures.정우_전공, ProfileFixtures.정우_이미지_주소, jobTagsToStringSet(ProfileFixtures.정우_직무_태그.get()), ProfileFixtures.정우_매칭_신청_여부)
                );
    }

    @DisplayName("조회하려는 페이지부터 회원이 10명이 넘으면 hasNext 필드 값은 true다")
    @Test
    void findMoreThanTenMember() {
        // given
        for (int i = 0; i < 11; i++) {
            Member dummy = Member.builder().email(new Email("dummy" + i + "@naver.com")).profile(Profile.create()).build();
            memberRepository.save(dummy);
        }

        // when
        MembersResponse response = searchingMemberService.getMembers(0L);

        // then
        assertThat(response.isHasNext()).isTrue();
    }

    private int calculateAge(String birthYear) {
        return LocalDate.now().getYear() - Integer.parseInt(birthYear) + 1;
    }

    private String genderToString(Gender gender) {
        return (gender.equals(Gender.FEMALE)) ? "여자" : "남자";
    }

    private Set<String> jobTagsToStringSet(Set<AttachedJobTag> attachedJobTags) {
        return attachedJobTags.stream()
                .map(AttachedJobTag::getJobTagName)
                .collect(Collectors.toSet());
    }

    private Set<String> skillTagsToStringSet(Set<AttachedSkillTag> attachedSkillTags) {
        return attachedSkillTags.stream()
                .map(AttachedSkillTag::getSkillTagName)
                .collect(Collectors.toSet());
    }

    private Set<String> availableDaysToStringSet(Set<DayOfWeek> availableDays) {
        return availableDays.stream()
                .map(DayOfWeek::toString)
                .collect(Collectors.toSet());
    }

    @DisplayName("")
    @Test
    void test() {
        // given
        Long a = 1L;
        Long b = 2L;
        // when
        System.out.println(a + "&" + b);
        // then

    }
}
