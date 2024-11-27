package com.ecyce.karma.domain.product.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyOptionRequest {

    JsonNullable<String> optionName;
    JsonNullable<Integer> optionPrice;

    public ModifyOptionRequest(JsonNullable<Long> optionId ,JsonNullable<String> optionName , JsonNullable<Integer> optionPrice){
        this.optionName = optionName;
        this.optionPrice = optionPrice;
    }
}
