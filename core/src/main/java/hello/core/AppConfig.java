package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// 설정 클래스
// 리팩토링 결과 후
/******************************************************************************************************
@Configuration 특징 // 이것을 붙이지 않으면 , 싱글톤을 보장하지 않음 뭔가 바뀐 것이 있는지 궁금해서 ...
스프링 프레임워크에서 **설정 정보(Configuration Information)**로 사용되는 클래스임을 명시
내부에 빈으로 등록된 객체가 존재하면 기존의 객채를 반환하고 없으면 새로 생성하여 반환 (따라서 싱글톤 패턴 유지)

 우리는 한번도 오류가 생기면 안된다 무조건 configuration 과 bean을 만들어야 한다.

 @configuration 과 @bean을 같이 사용하는 이유는 스프링 자체에서 싱글톤을 완벽 보장 : 프록시 설정으로(무조건 같이 사용)
 ******************************************************************************************************/
// 설정 관련된 어노테이션
//스프링 프레임워크에서 **설정 정보(Configuration Information)**로 사용되는 클래스임을 명시하는 어노테이션
@Configuration
/*******************************************************************************************************
 @ComponentScan : @Component 어노테이션이 붙은 클래스를 읽고 자동으로 빈 등록
**************************************************************************************************** */
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class))
public class AppConfig {
    /******************************************************************************************************
    MemberService 와 orderService 가 새로운 메소드를 사용하도록 수정하여 중복코드를 제거하고 의존관계를 명확히 정의
    각 서비스가 어떤 구현체에 의존하는지 한눈에 파악할수 있으며 ,
    나중에 구현 클래스를 변경해야 할때 해당 메소드만 수정하면 되므로 유지 보수성이 향상
     *******************************************************************************************************/
//    @Bean
    //@Bean : 메소드들을 모두 호출하여, 반환된 객체를 스프링 컨테이너에 등록
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }

    // MemorymemberRepository 생성을 분리하기 위해 메서드 추가
//    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    // FixDiscountPolicy 생성을 분리하기 위해 메서드 추가
//    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
