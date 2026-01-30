package hello.core.member;

public class MemberServiceImpl implements MemberService{

    //강한 결합 : 개발자가 직접 객체 의존성 주입
//    private final MemberRepository memberRepository = new MemomryMemberRepository();

    private MemberRepository memberRepository;

    // 생성자 주입 (느슨한 결합)
    public MemberServiceImpl(MemberRepository memberRepository) {
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
