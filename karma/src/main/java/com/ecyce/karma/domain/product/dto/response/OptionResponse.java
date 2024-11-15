package com.ecyce.karma.domain.product.dto.response;

import com.ecyce.karma.domain.product.entity.ProductOption;

public record OptionResponse(
        Long optionId,
        String optionName,
        int optionPrice
) {
    public  static OptionResponse from(ProductOption productOption){
        return new OptionResponse(
                productOption.getOptionId(),
                productOption.getOptionName(),
                productOption.getOptionPrice()
        );
    }
}