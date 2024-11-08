package com.ecyce.karma.domain.order.dto;

import com.ecyce.karma.domain.order.entity.Orders;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResponseDto {
    private Long orderId;
    private String orderState;
    private String orderStatus;
    private LocalDateTime createdAt;
    private String request;

    public static OrderCreateResponseDto from(Orders order) {
        return new OrderCreateResponseDto(
                order.getOrderId(),
                order.getOrderState()
                     .name(),
                order.getOrderStatus()
                     .name(),
                order.getCreatedAt(),
                order.getRequest()
        );
    }
}