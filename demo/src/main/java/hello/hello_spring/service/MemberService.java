package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    // memberService 에서 memberRepository 직접 new,fmf tkdydgkdu
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository; // 이거도 같이 생성이 되기 때문이다.
    }

    //회원 가입
    public Long join(Member member){
        // 중복 회원 검증
     validateDuplicateMember(member);

        // 중복회원이 아니면, 회원 가입
        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원 검증 메서드
    // isPresent() 메서드는 Optional 객체가 값을 가지고 있는 경우 true, 없다면 false반환
    // ifPresent() 메서드는 Optional 객체가 값을 가지고 있는 경우 실행, 없다면 구문 자체를 넘어감.
    private void validateDuplicateMember(Member member){
        Optional<Member> result =  memberRepository.findByName(member.getName());
        result.ifPresent( m -> {
            throw new IllegalThreadStateException("이미 존재하는 회원");
        });
    }
    // 전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    // id로 회원조회
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
