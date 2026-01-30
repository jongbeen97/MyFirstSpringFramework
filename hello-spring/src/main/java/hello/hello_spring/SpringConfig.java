package hello.hello_spring;


import hello.hello_spring.repository.JdbcTemplateMemberRepository;
import hello.hello_spring.repository.JpaMemberRepository;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    @Autowired
    private  DataSource dataSource; //JdbcTemplateMemberRepository 사용하기 위해 DI

    @Autowired
    private EntityManager em; // dataSource 대신 EntityManager추가
//JpaMemberRepository 를 사용하기 위해 DI

    @Bean
    public MemberService memberService(){
        return new MemberService((memberRepository()));
    }

    @Bean
    public MemberRepository memberRepository(){
        //JdbcTemplateMemberRepository에 JdbcTemplate객체를 담아서 생성.
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}

