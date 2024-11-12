package com.ecyce.karma.domain.product.dto.response;

import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductOption;
import com.ecyce.karma.domain.product.entity.ProductState;
import jakarta.persistence.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailResponse {

    private Long productId; // 상품 Id
    private Long userId; // 판매자 Id
    private String productName; // 상품 이름
    private int price; // 가격
    private String content; // 제품 소개
    private int duration; // 소요 기간
    private int rating; // 평점
    private ProductState productState; // 제품 공개 여부

    private int deliveryFee; // 배송비


    private String materialInfo; // 소재의 정보


    private String materialExample; // 소재 예시 사진


    private String buyerNotice; // 구매자 안내사항

    private List<OptionResponseDto> options;

    public ProductDetailResponse(Long productId , Long userId , String productName , int price , String content , int duration , int rating , ProductState productState,
            int deliveryFee , String materialInfo , String materialExample , String buyerNotice , List<OptionResponseDto> options ){
        this.productId = productId;
        this.userId = userId;
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
        this.options = options;
    }

    public static ProductDetailResponse from(Product product) {
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
                product.getMaterialExample(),
                product.getBuyerNotice(),
                product.getOptions() != null
                        ? product.getOptions().stream()
                        .map(OptionResponseDto::from)
                        .collect(Collectors.toList())
                        : new ArrayList<>()
        );
    }


}
