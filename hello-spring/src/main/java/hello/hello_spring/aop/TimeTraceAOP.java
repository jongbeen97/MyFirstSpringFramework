package hello.hello_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// AOP의 기본 모듈 , Advice + Pointcut 의 줄임말이다
// Advice : 타겟 ( 부가 기능을 부여할 대상 )에 제공할 부가기능을 담는다.
// pointcut : 타겟에 적용할 메서드를 선별하는 정규 표현식 입니다.
// pointcut : execution으로 시작이 됩니다.
// @Component : 스프링 빈으로 등록
@Aspect
@Component // @Bean은 광범위 , 그래서 이는 특정 부분의 관섬에서 시작을 한다는 의미
public class TimeTraceAOP {
    // @Around : AOP가 적용될 디렉터리 설정
    @Around("execution(*hello.hello_spring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint){
        long start = System.currentTimeMillis();
        System.out.println("START: "+joinPoint.toString());

        // AOP가 적용된 메서드 호출 전 동작
        try {
           return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            // AOP가 적용된 메서드 호출 후 동작
            long finish = System.currentTimeMillis();
            long duration = finish - start;
            System.out.println("END: "+joinPoint.toString()+ duration+"ms");
        }
    }
}
