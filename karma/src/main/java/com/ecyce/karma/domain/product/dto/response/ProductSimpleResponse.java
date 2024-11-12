package com.ecyce.karma.domain.product.dto.response;

import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductState;

public record ProductSimpleResponse(
        Long productId,
        Long userId,
        String productName,
        int price,
        int duration,
        ProductState productState,
        boolean isMarked
) {

    public static ProductSimpleResponse from(Product product , boolean isMarked){
        return new ProductSimpleResponse(
                product.getProductId(),
                product.getUser().getUserId(),
                product.getProductName(),
                product.getPrice(),
                product.getDuration(),
                product.getProductState(),
                isMarked
        );
    }
}
