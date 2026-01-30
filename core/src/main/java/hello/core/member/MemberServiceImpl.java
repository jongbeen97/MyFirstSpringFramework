package hello.core.member;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;

// 회사 자체 로컬 서버 사용
//@RequiredArgsConstructor
//@Service
public class MemberServiceImpl implements MemberService{
    // 자체 회원 저장소 역할 ( DB의 역할 ) , 하나의 DB에서 회원을 저장해야 함. 그래서 STATIC 공용공간임으로 , 정적 필드로 선언
    // 강한 결합 : 개발자가 직접 객체를 주입 ( 의존성을 주입하는 것 )
//    private final MemberRepository memberRepository = new MemoryMemberRepository(); //  바뀔때 마다 수정을 함 ( 강한 결함 ) , 이게 싫다는 것이다. 이거는 원래라면 강한 결합이 된다

    private  MemberRepository memberRepository;

    //생성자 주입 ( 느슨한 결합 )
    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
