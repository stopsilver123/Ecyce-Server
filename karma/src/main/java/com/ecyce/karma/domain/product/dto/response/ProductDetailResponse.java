package com.ecyce.karma.domain.product.dto.response;

import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductState;
import jakarta.persistence.Column;

import java.util.List;
import java.util.stream.Collectors;

public record ProductDetailResponse(
        Long productId, // 상품 Id
        Long userId, // 판매자 Id
        String productName, // 상품 이름
        int price, // 가격
        String content, // 제품 소개
        int duration, // 소요 기간
        Integer rating, // 평점
        ProductState productState, // 제품 공개 여부
        Integer deliveryFee, // 배송비
        String materialInfo, // 소재의 정보
        String buyerNotice, // 구매자 안내사항

        List<OptionResponseDto> options

) {
    public static ProductDetailResponse from(Product product){
        return new ProductDetailResponse(
                product.getProductId(),
                product.getUser().getUserId(),
                product.getProductName(),
                product.getPrice(),
                product.getContent(),
                product.getDuration(),
                product.getRating(),
                product.getProductState(),
                product.getDeliveryFee(),
                product.getMaterialInfo(),
                product.getBuyerNotice(),
                product.getOptions().stream()
                        .map(OptionResponseDto :: from )
                        .collect(Collectors.toList())
        );
    }


    public static ProductDetailResponse of(Product product , List<OptionResponseDto> options){
        return new ProductDetailResponse(
                product.getProductId(),
                product.getUser().getUserId(),
                product.getProductName(),
                product.getPrice(),
                product.getContent(),
                product.getDuration(),
                product.getRating(),
                product.getProductState(),
                product.getDeliveryFee(),
                product.getMaterialInfo(),
                product.getBuyerNotice(),
                options
        );
    }

}