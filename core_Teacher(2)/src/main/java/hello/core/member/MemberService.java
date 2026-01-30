package hello.core.member;

public interface MemberService {
    // 서비스의 메서드는 클라이언트에게 익숙한 단어를 사용할것!

    // 회원 가입
    void join(Member member);

    // 회원 조회
    Member findMember(Long memberId);
}
