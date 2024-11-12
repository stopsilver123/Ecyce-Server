package com.ecyce.karma.domain.product.dto.response;

import com.ecyce.karma.domain.product.entity.ProductOption;

public record OptionResponseDto(
        Long optionId,
        String optionName,
        int optionPrice
) {
    public  static OptionResponseDto from(ProductOption productOption){
        return new OptionResponseDto(
                productOption.getOptionId(),
                productOption.getOptionName(),
                productOption.getOptionPrice()
        );
    }
}
