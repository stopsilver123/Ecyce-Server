package com.ecyce.karma.domain.product.dto.request;

import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionRequestDto {

    private String optionName;
    private int optionPrice;

    public OptionRequestDto(String optionName , int optionPrice){
        this.optionName = optionName;
        this.optionPrice = optionPrice;
    }

    public static ProductOption toEntity(Product product , OptionRequestDto requestDto){
        return ProductOption.builder()
                .optionName(requestDto.getOptionName())
                .optionPrice(requestDto.getOptionPrice())
                .product(product)
                .build();
    }

}
