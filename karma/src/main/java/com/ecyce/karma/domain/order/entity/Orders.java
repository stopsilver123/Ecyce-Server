package com.ecyce.karma.domain.order.entity;

import com.ecyce.karma.domain.pay.entity.Pay;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.review.entity.Review;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
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
    private String orderOption;

    @Column(nullable = false)
    private String invoiceNumber;

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

    @Builder
    public Orders(String request , OrderState orderState , String orderOption , String invoiceNumber , OrderStatus orderStatus , User sellerUser , User buyerUser , Product product){
        this.request = request;
        this.orderState = orderState;
        this.orderOption = orderOption;
        this.invoiceNumber = invoiceNumber;
        this.orderStatus = orderStatus;
        this.sellerUser = sellerUser;
        this.buyerUser = buyerUser;
        this.product = product;
    }


}
