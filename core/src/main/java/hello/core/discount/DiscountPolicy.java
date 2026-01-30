package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {
    //할인 기능 (회원 등급, 할인 정책이 정률일 경우 대비 )
    int discount(Member member, int price);

}
