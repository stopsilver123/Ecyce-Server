package com.ecyce.karma.domain.order.dto;

import com.ecyce.karma.domain.order.entity.Orders;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOverviewResponseDto {
    private Long orderId; // 주문 ID
    private String productName; // 상품 이름
    private String productOption; // 상품 옵션
    private int orderCount; // 주문 수량
    private String orderState; // 주문 상태
    private LocalDateTime createdAt; // 주문 생성일
    private int totalPrice; // 총 금액
//    private ProductImageDto productImages; // 상품 이미지 정보

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductImageDto {
        private Long productImageId; // 상품 이미지 ID
        private String productImageUrl; // 상품 이미지 URL
    }

    /**
     * 정적 메서드: 엔티티를 DTO로 변환
     */
    public static OrderOverviewResponseDto fromEntity(Orders order) {
        return OrderOverviewResponseDto.builder()
                                       .orderId(order.getOrderId())
                                       .productName(order.getProduct().getProductName())
                                       .productOption(order.getProductOption().getOptionName())
                                       .orderCount(order.getOrderCount())
                                       .orderState(order.getOrderState().name())
                                       .createdAt(order.getCreatedAt())
                                       .totalPrice(order.getPay().getPayAmount())
//                               .productImages(ProductImageDto.builder()
//                                                             .productImageId(order.getProduct().)
//                                                             .productImageUrl(order.getProductImageUrl())
//                                                             .build())
                                       .build();
    }
}