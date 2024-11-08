package com.ecyce.karma.domain.product.entity;

import com.ecyce.karma.domain.order.entity.Orders;
import com.ecyce.karma.domain.sales.entity.Sales;
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
    private Long price;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long duration;

    @Column(nullable = false)
    private Long rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductState productState;

    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
    List<Orders> orders  = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();


    @Builder
    public Product(User user , String productName , Long price , String content , Long duration , Long rating , ProductState productState){
        this.user = user;
        this.productName = productName;
        this.price = price;
        this.content = content;
        this.duration = duration;
        this.rating = rating;
        this.productState = productState;
    }

}
