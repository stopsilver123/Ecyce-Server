package com.ecyce.karma.domain.pay.entity;

import com.ecyce.karma.domain.order.entity.Orders;
import com.ecyce.karma.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payId")
    private Long payId;

    @OneToOne
    @JoinColumn(name = "orderId" ,unique = true , updatable = false, nullable = false)
    private Orders orders;

    @Column(nullable = false)
    private Long payAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayStatus payStatus;

    @Builder
    public Pay(Orders orders , Long payAmount , PayStatus payStatus){
        this.orders = orders;
        this.payAmount = payAmount;
        this.payStatus =payStatus;
    }


}
