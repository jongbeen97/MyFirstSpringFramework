package hello.core.member;

public interface MemberRepository {
    // 리포지토리의 메서드는 DB 데이터베이스 관련 단어를 사용할 것!

    //회원 가입(저장)
    void save(Member member);

    //회원 조회
    Member findById(Long memberId);
}
