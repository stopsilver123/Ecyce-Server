package com.ecyce.karma.domain.product.entity;

import com.ecyce.karma.domain.order.entity.Orders;
import com.ecyce.karma.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "userId" ,updatable = false, nullable = false)
    private User user;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int duration;

    @Column
    private Integer rating;  // 점수는 아무도 리뷰를 쓰지 않은 경우 , 없을 수 있음

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductState productState;

    @Column
    private int deliveryFee; // 배송비

    @Column
    private String materialInfo; // 소재의 정보

    @Column
    private String materialExample; // 소재 예시 사진

    @Column
    private String buyerNotice; // 구매자 안내사항



    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders  = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();


    @Builder
    public Product(User user , String productName , int price , String content , int duration , int rating , ProductState productState,
    int deliveryFee , String materialInfo , String materialExample , String buyerNotice){
        this.user = user;
        this.productName = productName;
        this.price = price;
        this.content = content;
        this.duration = duration;
        this.rating = rating;
        this.productState = productState;
        this.deliveryFee = deliveryFee;
        this.materialInfo = materialInfo;
        this.materialExample = materialExample;
        this.buyerNotice = buyerNotice;
    }





}
