package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {
    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    //vip 회원
    @Test
    @DisplayName("VIP 회원인 경우 10% 할인 적용") // 어노테이션 주석
    void discount_vip() {
       //given
        Member member = new Member(1L,"memberVIP", Grade.VIP);
        //When
        int discount = discountPolicy.discount(member,100000);
        //Then
        assertEquals(10000,discount);


    }
    // 일반 회원
    @Test
    void discount_basic() {
        //given
        Member member = new Member(1L,"memberVIP", Grade.BASIC);
        //When
        int discount = discountPolicy.discount(member,100000);
        //Then
        assertEquals(10000,discount);


    }
}