package hello.core.order;

import lombok.Data;

@Data
public class Order {
    private Long memberId;
    private String itemName;
    private int itemPrice;
    private int discountPrice;

    public Order(Long memberId, String itemName, int itemPrice, int discountPrice) {
        this.memberId = memberId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
    }

    // 주문에서 할인이 적용된 가격 계산 기능 구현
    public int calculatePrice() {
        return itemPrice - discountPrice;
    }

}