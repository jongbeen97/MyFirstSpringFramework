package hello.core.member;

import org.springframework.stereotype.Component;

@Component
public interface MemberService {
    //서비스의 메서드는 클라이언트에게 익숙한 단어를 사용할 것!

    //회원 가입을 하면 서비스 임플 자바 파일에서 실행하도록 도와주는 것.
    void join(Member member);

    //회원 조회
    Member findMember(Long memberId);
}
