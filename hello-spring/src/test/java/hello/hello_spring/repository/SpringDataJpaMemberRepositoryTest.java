package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //SpringConfig등 다른 스프링 파일을 읽도록 처리(필수)
@Transactional// Test이후 DB데이터 테스트 전으로 롤백 ( 필수 )
class SpringDataJpaMemberRepositoryTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JpaMemberRepository jpaMemberRepository;

    @Test
    void findByName() {
        Member member = new Member();
        member.setName("jj");

        Long saveId =memberService.join(member);

        Member findMember = memberRepository.findById(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
}