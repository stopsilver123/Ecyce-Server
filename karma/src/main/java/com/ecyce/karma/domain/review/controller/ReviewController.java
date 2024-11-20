package com.ecyce.karma.domain.review.controller;

import com.ecyce.karma.domain.auth.customAnnotation.AuthUser;
import com.ecyce.karma.domain.review.dto.ReviewDetailDto;
import com.ecyce.karma.domain.review.dto.ReviewRequestDto;
import com.ecyce.karma.domain.review.dto.ReviewResponseDto;
import com.ecyce.karma.domain.review.service.ReviewService;
import com.ecyce.karma.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    /* 리뷰 생성 */
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@AuthUser User user, @RequestBody ReviewRequestDto requestDto) {
        ReviewResponseDto responseDto = reviewService.create(user, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /* 후기 상세 조회 */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailDto> getReview(@PathVariable("reviewId") Long reviewId) {
        ReviewDetailDto responseDto = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(responseDto);
    }

    /* 후기 삭제 */
}
