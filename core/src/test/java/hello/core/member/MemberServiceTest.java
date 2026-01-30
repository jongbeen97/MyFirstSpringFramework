package hello.core.member;

import hello.core.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/*
MemberService 에서 작성한 회원 가입,조회 기능 테스트
테스트를 위해 JUnit5 사용
 */
class MemberServiceTest {
    // 서비스를 테스트를 해야 하니 멤버 서비스 기능 테스트를 위해 MemberServiceImpl() 객체 생성을 해야 함
//    MemberService memberService = new MemberServiceImpl();

    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();//리팩터링 해서 폴리시 적용해서 주어야 하기에 ,
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        Member member = new Member(1L , "memberA",Grade.VIP);

        // DB 연결을 위해 사용
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        assertEquals(member,findMember);
    }
}