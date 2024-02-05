package com.tuti.fixtures;

import com.tuti.member.domain.Member;
import com.tuti.member.domain.vo.*;

public class MemberFixtures {
    public static final String 정우_이름 = "이정우";
    public static final Email 정우_이메일 = new Email("jeongwoo@naver.com");
    public static final Gender 정우_성별 = Gender.MALE;
    public static final String 정우_비밀번호 = "secret";
    public static final Role 정우_권한 = Role.STUDENT;
    public static final String 정우_생년 = "1999";
    public static final String 정우_생일 = "0225";

    public static final String 혜린_이름 = "윤혜린";
    public static final Email 혜린_이메일 = new Email("hyerin@naver.com");
    public static final Gender 혜린_성별 = Gender.FEMALE;
    public static final String 혜린_비밀번호 = "secret";
    public static final Role 혜린_권한 = Role.STUDENT;
    public static final String 혜린_생년 = "1995";
    public static final String 혜린_생일 = "0705";

    public static final String 심규_이름 = "육심규";
    public static final Email 심규_이메일 = new Email("simgyu@naver.com");
    public static final Gender 심규_성별 = Gender.MALE;
    public static final String 심규_비밀번호 = "secret";
    public static final Role 심규_권한 = Role.STUDENT;
    public static final String 심규_생년 = "1994";
    public static final String 심규_생일 = "0108";

    public static final String 주영_이름 = "박주영";
    public static final Email 주영_이메일 = new Email("juyoung@naver.com");
    public static final Gender 주영_성별 = Gender.FEMALE;
    public static final String 주영_비밀번호 = "secret";
    public static final Role 주영_권한 = Role.STUDENT;
    public static final String 주영_생년 = "1994";
    public static final String 주영_생일 = "0814";

    public static final String 창환_이름 = "강창환";
    public static final Email 창환_이메일 = new Email("changhwan@naver.com");
    public static final Gender 창환_성별 = Gender.MALE;
    public static final String 창환_비밀번호 = "secret";
    public static final Role 창환_권한 = Role.STUDENT;
    public static final String 창환_생년 = "1999";
    public static final String 창환_생일 = "1228";


    public static Member 정우() {
        return Member.builder().name(정우_이름).email(정우_이메일).gender(정우_성별).password(정우_비밀번호).birthYear(정우_생년).birthDay(정우_생일)
                .profile(ProfileFixtures.정우()).build();
    }

    public static Member 혜린() {
        return Member.builder().name(혜린_이름).email(혜린_이메일).gender(혜린_성별).password(혜린_비밀번호).birthYear(혜린_생년).birthDay(혜린_생일).profile(Profile.create())
                .profile(ProfileFixtures.혜린()).build();
    }

    public static Member 심규() {
        return Member.builder().name(심규_이름).email(심규_이메일).gender(심규_성별).password(심규_비밀번호).birthYear(심규_생년).birthDay(심규_생일).profile(Profile.create())
                .profile(ProfileFixtures.심규()).build();
    }

    public static Member 주영() {
        return Member.builder().name(주영_이름).email(주영_이메일).gender(주영_성별).password(주영_비밀번호).birthYear(주영_생년).birthDay(주영_생일).profile(Profile.create())
                .profile(ProfileFixtures.주영()).build();
    }

    public static Member 창환() {
        return Member.builder().name(창환_이름).email(창환_이메일).gender(창환_성별).password(창환_비밀번호).birthYear(창환_생년).birthDay(창환_생일).profile(Profile.create())
                .profile(ProfileFixtures.창환()).build();
    }
}
