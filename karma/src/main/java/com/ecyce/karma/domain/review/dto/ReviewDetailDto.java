package com.ecyce.karma.domain.review.dto;

import com.ecyce.karma.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetailDto {
    private String reviewerName;
    private LocalDate reviewDate;
    private String content;
    private Integer rating;

    public static ReviewDetailDto from(Review review) {
        return new ReviewDetailDto(
                review.getUser().getNickname(),
                review.getCreatedAt().toLocalDate(),
                review.getContent(),
                review.getRating()
        );
    }
}
