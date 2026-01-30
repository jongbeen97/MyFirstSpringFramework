package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemomryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @ComponentScan : 스프링이 자동으로 @Component 어노테이션이 붙은 클래스를 찾아서 스프링 빈으로 등록
// basePackages 속성 : 스캔이 필요한 위치(패키지) 지정 -> 시간 단축(설정하지 않으면 모든 파일을 다 읽음)
// @ComponentScan.Filter : 특정 클래스를 컴포넌트 스캔 대상에서 제외할때 사용
//                         @Configuration 어노테이션이 붙은 클래스 제외
@ComponentScan(basePackages = "hello.core", excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class))
public class AutoAppConfig {

    // AutoAppConfig 클래스 자체에는 직접적인 설정 필요 없음.
    // @ComponentScan을 통해 자동으로 스프링 빈들을 등록
    // @Component, @Service, @Repository, @Controller 등이 붙은 클래스들을 자동으로 빈으로 스프링에 등록

    // @Component : 컴포넌트 스캔에 사용
    // @Controller : 스프링 MVC 컨트롤러에 사용
    // @Service : 스프링 비즈니스 로직에 사용
    // @Repository : 스프링 데이터 접근 계층에서 사용

}
