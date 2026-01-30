package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

// 고정 할인 정책
public class FixDiscountPolicy implements  DiscountPolicy{
    // 구매 금액과 상관 없이 1000원 할인
    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        // VIP 등급만 1000원 고정 할인
        // 등급은 상수이므로 == 비교
        if (member.getGrade() == Grade.VIP){
            return discountFixAmount;
        }
        return 0;
    }
}
