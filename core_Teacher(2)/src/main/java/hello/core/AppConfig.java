package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemomryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// 리팩토링 결과 후
/*
    @Configuration 어노테이션 특징
    스프링 프레임워크에서 **설정 정보(Configuration Information)**를 담고 있는 클래스임을 명시
    내부에 @Bean으로 등록된 객체가 존재하면 기존의 객체를 반환하고 없으면 새로 생성하여 반환(싱글톤 패턴을 유지)
    @Configuration + @Bean : Spring 자체에서 싱글톤 완벽 보장: 프록시 설정(무조건 같이 사용)
 */
@Configuration
public class AppConfig {

    /*
    memberService()와 orderService()가 새로운 메소드를 사용하도록 수정하여 중복 코드를 제거하고 의존관계를 명확히 정의
    각 서비스가 어떤 구현체에 의존하는지 한눈에 파악할 수 있으며,
    나중에 구현 클래스를 변경해야 할 때 해당 메소드만 수정하면 되므로 유지보수성이 향상
     */

    // @Bean : 메소드들을 모두 호출하여, 반환된 객체를 스프링 컨테이너에 등록
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }

    // MemomryMemberRepository 생성을 분리하기 위해 메서드 추가
    @Bean
    public MemberRepository memberRepository() {
        return new MemomryMemberRepository();
    }

    // FixDiscountPolicy 생성을 분리하기 위해 메서드 추가
    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
