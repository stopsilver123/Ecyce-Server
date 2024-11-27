package com.ecyce.karma.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private Long orderId;
    private String content;
    private Integer rating;
}
