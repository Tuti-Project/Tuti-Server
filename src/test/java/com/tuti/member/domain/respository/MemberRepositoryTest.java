package com.tuti.member.domain.respository;

import com.tuti.common.RepositoryTest;
import com.tuti.fixtures.MemberFixtures;
import com.tuti.member.domain.Member;
import com.tuti.member.repository.MemberRepository;
import com.tuti.member.domain.vo.ApplyMatchingStatus;
import com.tuti.member.domain.vo.Email;
import com.tuti.member.service.request.UpdateMyPageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RepositoryTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(MemberFixtures.정우());
        memberRepository.save(MemberFixtures.혜린());
        memberRepository.save(MemberFixtures.심규());
        memberRepository.save(MemberFixtures.주영());
    }

    @DisplayName("신규 사용자의 정보를 저장한다")
    @Test
    void saveMember() {
        // given, when
        Member 창환 = memberRepository.save(MemberFixtures.창환());

        // then
        assertThat(창환).isNotNull();
    }

    @DisplayName("사용자를 조회한다")
    @Test
    void findMember() {
        // given
        Member member = memberRepository.findByEmail(MemberFixtures.정우_이메일).get();

        // when
        Member findMember = memberRepository.findByEmail(member.getEmail()).get();

        // then
        assertThat(member).isEqualTo(findMember);
    }

    @DisplayName("존재하지 않는 사용자를 조회한다")
    @Test
    void findNotExistMember() {
        // when
        Optional<Member> member = memberRepository.findByEmail(new Email("notExist@naver.com"));

        // then
        assertThat(member).isEmpty();
    }

    @DisplayName("사용자의 프로필 정보를 수정한다")
    @Test
    void updateMemberProfile() {
        // given
        Member member = memberRepository.findByEmail(MemberFixtures.정우_이메일).get();
        UpdateMyPageRequest updateRequest = createUpdateRequest("단국대학교", "소프트웨어학과", "image-uri");

        // when
        member.updateProfile(updateRequest);

        // then
        assertThat(member.getProfile())
                .extracting("university", "major", "imageUrl")
                .containsExactly("단국대학교", "소프트웨어학과", "image-uri");
    }

    private UpdateMyPageRequest createUpdateRequest(String university, String major, String imageUrl) {
        return UpdateMyPageRequest.builder()
                .university(university)
                .major(major)
                .imageUrl(imageUrl)
                .description("")
                .matchingDescription("")
                .applyMatchingStatus(ApplyMatchingStatus.OFF)
                .availableHours("")
                .jobTags(Collections.emptySet())
                .skillTags(Collections.emptySet())
                .availableDays(Collections.emptySet())
                .build();
    }
}
