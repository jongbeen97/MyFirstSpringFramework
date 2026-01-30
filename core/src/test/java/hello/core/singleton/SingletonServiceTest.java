package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class SingletonServiceTest {

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void getInstance() {
        // 기본 생성자를 프라이빗 접근 제한 걸어둬서 외부에서 인스턴스 생성 불가능
//        SingletonService s = new singletonservice ();
        //1. Getinstance()를 호출하여 객체 가져옴
        SingletonService s1 = SingletonService.getInstance();
        //2. 다시 한번 Getinstance()를 호출하여 객체 가져옴
        SingletonService s2 = SingletonService.getInstance();

        System.out.println("SingletonService1 :"+s1);
        System.out.println("SingletonService2 :"+s2);
        // 실제로 참조된 주소 값이 같다면 같은 객체이고요,
        // 실제로 참조된 주소 값이 다르다면요 다른 객체 입니다.
        assertEquals(s1,s2); // 이 둘은 같은 객체이니 테스트 통과가 뜨는 것 !

        s1.logic();
        s2.logic();

    }

    // 스프링 컨테이너는 자체가 싱글톤 패턴 방식을 적용하여 bean을 관리하고 있음.
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContaionr(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService",MemberService.class);
        MemberService memberService2 = ac.getBean("memberService",MemberService.class);

        System.out.println(memberService1);
        System.out.println(memberService2);

        assertEquals(memberService1,memberService2);
    }

    @Test
    @DisplayName("AppConfig 싱글톤 패턴 확인 -> 다른 객체로 나옵니다. ")
    void appConfigTest(){
        AppConfig appConfig1 = new AppConfig();
        AppConfig appConfig2 = new AppConfig();
        assertEquals(appConfig1,appConfig2);

    }
}