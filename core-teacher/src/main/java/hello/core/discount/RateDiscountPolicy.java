package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

// 정률 할인 정책
public class RateDiscountPolicy implements DiscountPolicy{
    // 구매 금액 대비 10% 할인 적용
    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        // VIP 등급만 10% 할인
        // 등급은 상수이므로 == 비교
        if(member.getGrade() == Grade.VIP){
            return price * discountPercent / 100;
        }
        return 0;
    }
}
