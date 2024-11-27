package com.ecyce.karma.domain.product.dto.request;

import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductState;
import com.ecyce.karma.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRequest {
    private String productName;
    private int price ;
    private String content;
    private int duration;
    private ProductState productState;
    private Integer deliveryFee; // 배송비
    private String materialInfo; // 소재의 정보
    private String buyerNotice; // 구매자 안내사항
    private List<OptionRequest> options;

    public ProductRequest(String productName , int price , String content , int duration , ProductState productState ,
                          Integer deliveryFee , String materialInfo , String buyerNotice,
                          List<OptionRequest> optionRequest){
       this.productName = productName;
       this.price = price;
       this.content = content;
       this.duration = duration;
       this.productState = productState;
       this.deliveryFee = deliveryFee;
       this.materialInfo = materialInfo;
       this.buyerNotice = buyerNotice;
       this.options = optionRequest;
   }

   public static Product toEntity(User user  , ProductRequest requestDto){

        return Product.builder()
                .user(user)
                .productName(requestDto.getProductName())
                .content(requestDto.getContent())
                .price(requestDto.getPrice())
                .duration(requestDto.getDuration())
                .deliveryFee(requestDto.getDeliveryFee())
                .materialInfo(requestDto.getMaterialInfo())
                .buyerNotice(requestDto.getBuyerNotice())
                .rating(0)
                .productState(ProductState.ON_SALE) // 처음에는 판매중 상태
                .build();
   }


}
