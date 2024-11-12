package com.ecyce.karma.domain.product.dto.response;

import com.ecyce.karma.domain.product.entity.ProductOption;

public class OptionResponseDto{

    private Long optionId;
    private String optionName;
    private int optionPrice;

    public OptionResponseDto(Long optionId , String optionName , int optionPrice){
        this.optionId = optionId;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
    }

    public  static OptionResponseDto from(ProductOption productOption){
        return new OptionResponseDto(
                productOption.getOptionId(),
                productOption.getOptionName(),
                productOption.getOptionPrice()
        );
    }
}
