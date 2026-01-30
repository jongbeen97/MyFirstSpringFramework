package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class JpaMemberRepository implements MemberRepository {

    //JPA 라이브러리를 받으면 스프링은 자동으로 EntityManager 객체를 생성해준다.
    //JPA EntityManager 객체로 모든 동작을 수행
    private EntityManager em;

    // 생성자 주입 : JPA는 모든 동작을 Em으로 하다 보니 EM 에 의존적이다 . 그래서 한다.
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // persist(); : 영속성 Context에 객체를 저장
        // 즉,메서드 호출시 실제 INSERT SQL문 수행
        em.persist(member);
        return member;
    }

    //ID(PK)로 조회
    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class,id);
        return Optional.ofNullable(member);
    }
    //PK가 아닌 조건으로 여러 건 조회시 JPQL을 직접 구현해야 합니다.
    @Override
    public List findAll() {
        return em.createQuery(
          "select m from Member m ",
                // Member 엔티티 ( 맴버 개체 ) 기준으로 쿼리 작성
                Member.class// 주의 점 : 이 맴버가 무슨 맴버인지 ? 맞춰주어야 한다. 그래서 Member.class를 넣어줘야 함
        ).getResultList();//그리고 리스트를 가져올 것이기에 이걸 써야 함
    }

    // :변수명 = 파라미터 바인딩 사용하는 방법
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery(
          "select m from Member m where m.name = :name",
            Member.class
        ).setParameter("name", name).getResultList();
        // 여러 결과 중 하나만 반환(중복 이름 고려)
        return result.stream().findAny();
    }

    // 추가해야 할 메서드
    @Override
    public void deleteById(Long id) {
        // 먼저 삭제할 대상을 조회합니다.
        Member member = em.find(Member.class, id);
        // 대상이 존재하면 삭제를 진행합니다.
        if (member != null) {
            em.remove(member);
        }
    }
}
