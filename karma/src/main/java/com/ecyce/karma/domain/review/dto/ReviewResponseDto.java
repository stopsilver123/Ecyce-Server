package com.ecyce.karma.domain.review.dto;

import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductOption;
import com.ecyce.karma.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private String reviewerName;
    private Long productId;
    private String productName;
//    private String productImage;
    private String productOption;
    private String content;
    private Integer rating;

    public static ReviewResponseDto from(Review review) {
        Product product = review.getOrders().getProduct();
        ProductOption productOption = review.getOrders().getProductOption();

        return new ReviewResponseDto(
                review.getReviewId(),
                review.getUser().getNickname(),
                product.getProductId(),
                product.getProductName(),
                productOption.getOptionName(),
                review.getContent(),
                review.getRating()
        );
    }
}
