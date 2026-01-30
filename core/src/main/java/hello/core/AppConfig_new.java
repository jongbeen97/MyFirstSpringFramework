package hello.core;

import hello.core.discount.DiscountPolicy;
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


@Configuration
// @componentScsn : 스프링이 자동으로 컴포넌트 어노테이션이 붙은 클래스를 찾아서 스프링 빈으로 등록
// basePackages 속성 : 스캔이 필요한 위치(패키지) 지정 <그 위치가 패키지 단이라는 것 -> 시간 단축을 위해 설정하지 않으면 모든 파일을 다 읽음.>
// @ComponentScan.Filter : 특정 클래스를 컴포넌트 스캔 대상에서 제외할 때 사용
//                        @Configuration 어노테이션이 붙은 클래스 제외


@ComponentScan(basePackages = "hello.core", excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class))
public class AppConfig_new {

    /*
    ***********************************************************************************************************
     AutoAppConfig 클래스 자체에는 직접적인 설정이 필요 없다.
     @ ComponentScan을 통해 자동으로 스프링 빈들을 등록
     @Component @Service @Repository @Controller 등이 붙은 클래스들을 자동으로 빈으로 스프링에 등록
     << 각각의 어노테이션에 대한 설명 >>
     @Component : 컴포넌트 스캔에 사용
    // @Controller : 스프링 MVC에서 컨트롤러로 사용하는 파일
    // @Service : 스프링에서 비즈니스 로직에 사용되는 파일이야
    // @Repository : 스프링 데이터 접근 계층에서 사용하는 것.
    * *********************************************************************************************************
    */
}
