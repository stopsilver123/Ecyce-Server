package com.ecyce.karma.domain.order.dto;

import com.ecyce.karma.domain.order.entity.Orders;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReadResponseDto {
    // 주문 정보
    private Long orderId;
    private String productOption;
    private String orderState;
    private String orderStatus;
    private String request;
    private LocalDateTime createdAt;
    // 사용자 정보
    private String name;
    private String phone;
//    private String address;
    // 결제 정보
    private Long price;


    public static OrderReadResponseDto from(Orders order) {
        return new OrderReadResponseDto(
                order.getOrderId(),
                order.getProductOption().getTitle(),
                order.getOrderState().name(),
                order.getOrderStatus().name(),
                order.getRequest(),
                order.getCreatedAt(),
                order.getBuyerUser().getNickname(),
                order.getBuyerUser().getPhoneNumber(),
//                order.getBuyerUser().getAddress()
                order.getPay().getPayAmount()
        );
    }
}
