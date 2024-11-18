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
    private String productName;
    private String productOption;
    private Integer orderCount;
    private String orderState;
    private String request;
    private LocalDateTime createdAt;

    // 안내 사항
    private String materialInfo;
    private String buyerNotice;

    // 판매자 정보
    private String sellerNickname;
    private String sellerName;
    private String sellerPhone;
    private String sellerAddress;

    // 구매자 정보
    private String buyerNickname;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;

    // 배송 정보
    private String invoiceNumber;

    // 결제 정보
    private Integer price;
    private Integer deliveryFee;
    private Integer totalPrice;

    public static OrderReadResponseDto from(Orders order) {
        return new OrderReadResponseDto(
                // 주문 정보
                order.getOrderId(),
                order.getProduct().getProductName(),
                order.getProductOption().getOptionName(),
                order.getOrderCount(),
                order.getOrderState().name(),
                order.getRequest(),
                order.getCreatedAt(),
                // 안내 사항
                order.getProduct().getMaterialInfo(),
                order.getProduct().getBuyerNotice(),
                // 판매자 정보
                order.getSellerUser().getNickname(),
                order.getSellerUser().getName(),
                order.getSellerUser().getPhoneNumber(),
                order.getSellerUser().getAddress().toString(),
                // 구매자 정보
                order.getBuyerUser().getNickname(),
                order.getBuyerUser().getName(),
                order.getBuyerUser().getPhoneNumber(),
                order.getBuyerUser().getAddress().toString(),
                // 배송 정보
                order.getInvoiceNumber() != null ? order.getInvoiceNumber() : "미발행",
                // 결제 정보
                (order.getProduct()
                     .getPrice()+order.getProductOption().getOptionPrice())* order.getOrderCount(),
                order.getProduct().getDeliveryFee(),
                order.getPay().getPayAmount()
        );
    }
}
