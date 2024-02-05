package com.tuti.fixtures;

import com.tuti.member.domain.vo.*;

import java.util.Set;

public class ProfileFixtures {
    public static final String 정우_대학 = "단국대학교";
    public static final String 정우_전공 = "소프트웨어학과";
    public static final String 정우_이미지_주소 = "image-uri";
    public static final String 정우_설명 = "개발 관련 업무를 희망합니다";
    public static final ApplyMatchingStatus 정우_매칭_신청_여부 = ApplyMatchingStatus.ON;
    public static final String 정우_업무_가능_시간 = "09:00 - 18:00";
    public static final AttachedJobTags 정우_직무_태그 = new AttachedJobTags(Set.of(new AttachedJobTag("IT/SW"), new AttachedJobTag("사무/행정")));
    public static final AttachedSkillTags 정우_능력_태그 = new AttachedSkillTags(Set.of(new AttachedSkillTag("한글/워드"), new AttachedSkillTag("엑셀")));
    public static final AvailableDays 정우_업무_가능_요일 = new AvailableDays(Set.of(DayOfWeek.MON, DayOfWeek.TUE, DayOfWeek.WED));

    public static final String 혜린_대학 = "한국외국어대학교";
    public static final String 혜린_전공 = "건축학과";
    public static final String 혜린_이미지_주소 = "image-uri";
    public static final String 혜린_설명 = "건축 관련 업무를 희망합니다";
    public static final ApplyMatchingStatus 혜린_매칭_신청_여부 = ApplyMatchingStatus.ON;
    public static final String 혜린_업무_가능_시간 = "09:00 - 18:00";
    public static final AttachedJobTags 혜린_직무_태그 = new AttachedJobTags(Set.of(new AttachedJobTag("디자인"), new AttachedJobTag("영업/상담")));
    public static final AttachedSkillTags 혜린_능력_태그 = new AttachedSkillTags(Set.of(new AttachedSkillTag("피그마"), new AttachedSkillTag("포토샵")));
    public static final AvailableDays 혜린_업무_가능_요일 = new AvailableDays(Set.of(DayOfWeek.MON, DayOfWeek.TUE, DayOfWeek.WED, DayOfWeek.THU, DayOfWeek.FRI));

    public static final String 심규_대학 = "단국대학교";
    public static final String 심규_전공 = "소프트웨어학과";
    public static final String 심규_이미지_주소 = "image-uri";
    public static final String 심규_설명 = "개발 관련 업무를 희망합니다";
    public static final ApplyMatchingStatus 심규_매칭_신청_여부 = ApplyMatchingStatus.ON;
    public static final String 심규_업무_가능_시간 = "09:00 - 18:00";
    public static final AttachedJobTags 심규_직무_태그 = new AttachedJobTags(Set.of(new AttachedJobTag("IT/SW"), new AttachedJobTag("사무/행정")));
    public static final AttachedSkillTags 심규_능력_태그 = new AttachedSkillTags(Set.of(new AttachedSkillTag("한글/워드"), new AttachedSkillTag("엑셀")));
    public static final AvailableDays 심규_업무_가능_요일 = new AvailableDays(Set.of(DayOfWeek.MON, DayOfWeek.TUE, DayOfWeek.WED));

    public static final String 주영_대학 = "연세대학교";
    public static final String 주영_전공 = "건축학과";
    public static final String 주영_이미지_주소 = "image-uri";
    public static final String 주영_설명 = "건축 관련 업무를 희망합니다";
    public static final ApplyMatchingStatus 주영_매칭_신청_여부 = ApplyMatchingStatus.ON;
    public static final String 주영_업무_가능_시간 = "09:00 - 18:00";
    public static final AttachedJobTags 주영_직무_태그 = new AttachedJobTags(Set.of(new AttachedJobTag("영업/상담"), new AttachedJobTag("광고/홍보")));
    public static final AttachedSkillTags 주영_능력_태그 = new AttachedSkillTags(Set.of(new AttachedSkillTag("시장조사"), new AttachedSkillTag("캐드")));
    public static final AvailableDays 주영_업무_가능_요일 = new AvailableDays(Set.of(DayOfWeek.MON, DayOfWeek.TUE, DayOfWeek.WED, DayOfWeek.THU, DayOfWeek.FRI, DayOfWeek.SAT, DayOfWeek.SUN));

    public static final String 창환_대학 = "단국대학교";
    public static final String 창환_전공 = "소프트웨어학과";
    public static final String 창환_이미지_주소 = "image-uri";
    public static final String 창환_설명 = "개발 관련 업무를 희망합니다";
    public static final ApplyMatchingStatus 창환_매칭_신청_여부 = ApplyMatchingStatus.ON;
    public static final String 창환_업무_가능_시간 = "09:00 - 18:00";
    public static final AttachedJobTags 창환_직무_태그 = new AttachedJobTags(Set.of(new AttachedJobTag("IT/SW"), new AttachedJobTag("사무/행정")));
    public static final AttachedSkillTags 창환_능력_태그 = new AttachedSkillTags(Set.of(new AttachedSkillTag("한글/워드"), new AttachedSkillTag("엑셀")));
    public static final AvailableDays 창환_업무_가능_요일 = new AvailableDays(Set.of(DayOfWeek.MON, DayOfWeek.TUE, DayOfWeek.WED));

    public static Profile 정우() {
        return Profile.builder().university(정우_대학).major(정우_전공).imageUrl(정우_이미지_주소).description(정우_설명).applyMatchingStatus(정우_매칭_신청_여부)
                .availableHours(정우_업무_가능_시간).attachedJobTags(정우_직무_태그).attachedSkillTags(정우_능력_태그).availableDays(정우_업무_가능_요일).build();
    }

    public static Profile 혜린() {
        return Profile.builder().university(혜린_대학).major(혜린_전공).imageUrl(혜린_이미지_주소).description(혜린_설명).applyMatchingStatus(혜린_매칭_신청_여부)
                .availableHours(혜린_업무_가능_시간).attachedJobTags(혜린_직무_태그).attachedSkillTags(혜린_능력_태그).availableDays(혜린_업무_가능_요일).build();
    }

    public static Profile 심규() {
        return Profile.builder().university(심규_대학).major(심규_전공).imageUrl(심규_이미지_주소).description(심규_설명).applyMatchingStatus(심규_매칭_신청_여부)
                .availableHours(심규_업무_가능_시간).attachedJobTags(심규_직무_태그).attachedSkillTags(심규_능력_태그).availableDays(심규_업무_가능_요일).build();
    }

    public static Profile 주영() {
        return Profile.builder().university(주영_대학).major(주영_전공).imageUrl(주영_이미지_주소).description(주영_설명).applyMatchingStatus(주영_매칭_신청_여부)
                .availableHours(주영_업무_가능_시간).attachedJobTags(주영_직무_태그).attachedSkillTags(주영_능력_태그).availableDays(주영_업무_가능_요일).build();
    }

    public static Profile 창환() {
        return Profile.builder().university(창환_대학).major(창환_전공).imageUrl(창환_이미지_주소).description(창환_설명).applyMatchingStatus(창환_매칭_신청_여부)
                .availableHours(창환_업무_가능_시간).attachedJobTags(창환_직무_태그).attachedSkillTags(창환_능력_태그).availableDays(창환_업무_가능_요일).build();
    }

}
