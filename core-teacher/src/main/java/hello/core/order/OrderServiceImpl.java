package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemomryMemberRepository;

public class OrderServiceImpl implements  OrderService{

    // 강한 결합
//    private MemberRepository memberRepository = new MemomryMemberRepository();
    private  MemberRepository memberRepository;

    // 강한 결합
//    private DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private DiscountPolicy discountPolicy;

    // 생성자 주입 (느슨한 결합)
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 회원 조회
        Member member = memberRepository.findById(memberId);
        // 할인 적용
        int discountPrice = discountPolicy.discount(member, itemPrice);
        // 주문 생성
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
