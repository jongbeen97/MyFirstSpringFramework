package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemomryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 리팩터링 결과 전
public class AppConfig_b {
    public MemberService memberService(){
        return new MemberServiceImpl(new MemomryMemberRepository());
    }

    public OrderService orderService(){
        return new OrderServiceImpl(
                new MemomryMemberRepository(),
                new FixDiscountPolicy());
    }
}
