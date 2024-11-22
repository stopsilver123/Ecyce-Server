package com.ecyce.karma.domain.product.dto.request;

import com.ecyce.karma.domain.product.entity.ProductOption;
import com.ecyce.karma.domain.product.entity.ProductState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyProductRequest {

    JsonNullable<String> productName;
    JsonNullable <Integer> price;
    JsonNullable<String> content;
    JsonNullable<Integer> duration;
    JsonNullable<Integer> rating;
    JsonNullable<ProductState> productState;
    JsonNullable<Integer> deliveryFee;
    JsonNullable<String> materialInfo;
    JsonNullable<String> buyerNotice;


    public  ModifyProductRequest(JsonNullable<String> productName , JsonNullable<Integer> price , JsonNullable<String> content,
                                 JsonNullable<Integer> duration , JsonNullable<Integer> rating , JsonNullable<ProductState> productState,
                                 JsonNullable<Integer> deliveryFee, JsonNullable<String> materialInfo , JsonNullable<String> buyerNotice

    ){

        this.productName = productName;
        this.price = price;
        this.content = content;
        this.duration = duration;
        this.rating = rating;
        this.productState = productState;
        this.deliveryFee = deliveryFee;
        this.materialInfo = materialInfo;
        this.buyerNotice = buyerNotice;

    }

}
