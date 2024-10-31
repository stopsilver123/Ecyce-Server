package com.ecyce.karma.domain.sales.entity;

import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesId")
    private Long salesId;

    @ManyToOne
    @JoinColumn(name = "userId" ,updatable = false, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId" ,updatable = false, nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long monthlyRevenue;

    @Column(nullable = false)
    private Long totalSales;

    @Column(nullable = false)
    private Long totalRevenue;

    @Builder
    public Sales(User user , Product product , Long monthlyRevenue , Long totalSales , Long totalRevenue){
        this.user = user;
        this.product = product;
        this. monthlyRevenue = monthlyRevenue;
        this.totalSales = totalSales;
        this.totalRevenue = totalRevenue;
    }
}
