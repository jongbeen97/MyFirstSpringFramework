package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //스프링 컨테이너를 실행해서 테스트 (DB접속 정보등을 가져오기 위함)
@Transactional //테스트 완료 후 DB에서 변경된 데이터를 자동으로 롤백(이전으로 돌려주는 것)
class JdbcTemplateMemberRepositoryTest {

    @Autowired
    JdbcTemplateMemberRepository jdbcTemplateMemberRepository;

    @Test
    void save() {
        // 준비
        Member member = new Member();
        member.setName("종빈");

        Member savedMember = jdbcTemplateMemberRepository.save(member);

        //실행
        String newName = "스프링 고수";
        jdbcTemplateMemberRepository.updateName(member.getId(),newName);

        //검증
        Member findMember = jdbcTemplateMemberRepository.findById(savedMember.getId()).get();
        assertThat(findMember.getName()).isEqualTo(newName);
        System.out.println("변경 후 이름:" +findMember.getName());


    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findAll() {
    }
}