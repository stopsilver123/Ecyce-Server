package com.ecyce.karma.domain.order.entity;

import com.ecyce.karma.domain.pay.entity.Pay;
import com.ecyce.karma.domain.pay.entity.PayStatus;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductOption;
import com.ecyce.karma.domain.review.entity.Review;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jdk.jshell.Snippet.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long orderId;

    @Column(nullable = false)
    private String request;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderState orderState;

    @Column(nullable = false)
    private Long orderCount;

    @Column
    private String invoiceNumber; // 송장번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerId", updatable = false, nullable = false)
    private User sellerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerId", updatable = false, nullable = false)
    private User buyerUser;

    @ManyToOne
    @JoinColumn(name = "productId" ,updatable = false, nullable = false)
    private Product product;

    @OneToOne(mappedBy = "orders" , cascade = CascadeType.ALL, orphanRemoval = true)
    private Pay pay;

    @OneToOne(mappedBy = "orders" , cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productOptionId", nullable = false)
    private ProductOption productOption;


    public Orders(String request , User sellerUser , User buyerUser , Product product, ProductOption productOption, Long orderCount, Long payAmount){
        this.request = request; // 유저 요구사항
        this.orderState = OrderState.접수완료; // 주문 진행 과정
        this.orderStatus = OrderStatus.수락대기; // 주문 대기, 승인, 거절
        this.sellerUser = sellerUser; // 판매자
        this.buyerUser = buyerUser; // 구매자
        this.product = product; // 구매할 상품
        this.productOption = productOption; // 상품 옵션
        this.orderCount = orderCount; // 상품 개수
        // 결제 정보 생성
        this.pay = Pay.builder()
                      .orders(this)
                      .payAmount(payAmount)
                      .payStatus(PayStatus.결제성공) // 추후 결제 관련 로직 생성 필요
                      .build();
    }

    public static Orders createOrder(String request, User seller, User buyer, Product product, ProductOption productOption, Long orderCount) {
        Long payAmount = (product.getPrice() + productOption.getSubPrice()) * orderCount; // 상품 가격 계산
        return new Orders(request, seller, buyer, product, productOption, orderCount, payAmount);
    }

    public void cancelOrder() {
        if (orderState != OrderState.접수완료 && orderState != OrderState.제작대기) {
            throw new IllegalStateException("현재 상태에서는 주문을 취소할 수 없습니다.");
        }
        this.orderState = OrderState.주문취소;
    }
}
