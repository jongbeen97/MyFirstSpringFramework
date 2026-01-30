package hello.core.member;

import hello.core.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
MemberService에서 작성한 회원가입, 조회 기능 테스트
테스트를 위해 JUnit5 사용
 */
class MemberServiceTest {
    // MemberService 기능 테스트를 위해 MemberServiceImpl 객체 생성
//    MemberService memberService = new MemberServiceImpl();

    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        // given
        Member member = new Member(1L, "memberA", Grade.VIP);
        // when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);
        // then
        assertEquals(member, findMember);
    }
}