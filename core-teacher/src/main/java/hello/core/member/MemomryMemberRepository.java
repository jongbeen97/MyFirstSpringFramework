package hello.core.member;

import java.util.HashMap;
import java.util.Map;

// 회사 자체 로컬 서버 사용
public class MemomryMemberRepository implements MemberRepository{
    // 자체 회원 저장소(DB)이므로 정적 필드로 선언
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
