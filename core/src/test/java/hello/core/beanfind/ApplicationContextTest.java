package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextTest {
    //스프링 컨테이너를 만들기
    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(AppConfig.class);


    @Test
    @DisplayName("애플리케이션컨텍스트에 존재하는 모든 빈 출력")
    void findAllBean(){
        // 스프링 컨테이너에 등록된 모든 빈의 이름을 문자열 배열로 저장
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) { // 빈의 이름들을 하나씩 빼서 이름과 객체를 호출하고 있다.
            // getBean(): 빈의 이름으로 빈 객체를 조회
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + " object = " + bean);
        }
    }

    @Test
    @DisplayName("개발자가 작성한 빈만 출력")
    void findMakeBean(){
        String[] beanAllNames = ac.getBeanDefinitionNames(); //  다 가지고 오긴 해야 함. 반복으로 비교해야 함.
        for (String beanName : beanAllNames) {
            // 각 빈에 대한 메타정보(정의)를 가져옴.
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanName);

            // BeanDefinition : 역할(Role)에 따라 빈 분류
            // BeanDefinition.Role.Application : 개발자가 등록한 빈
            // BeanDefinition.Role.INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beanName);
                System.out.println("name = " + beanName + " object = " + bean);
            }
        }


    }


}
