package com.ecyce.karma.domain.review.service;

import com.ecyce.karma.domain.order.entity.OrderState;
import com.ecyce.karma.domain.order.entity.Orders;
import com.ecyce.karma.domain.order.repository.OrdersRepository;
import com.ecyce.karma.domain.review.dto.ReviewDetailDto;
import com.ecyce.karma.domain.review.dto.ReviewRequestDto;
import com.ecyce.karma.domain.review.dto.ReviewResponseDto;
import com.ecyce.karma.domain.review.entity.Review;
import com.ecyce.karma.domain.review.repository.ReviewRepository;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.global.exception.CustomException;
import com.ecyce.karma.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrdersRepository ordersRepository;

    /* 리뷰 생성 */
    public ReviewResponseDto create(User user, ReviewRequestDto requestDto) {
        Orders order = ordersRepository.findByOrderId(requestDto.getOrderId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        // 리뷰 작성 가능 여부 검증
        validateReviewPermissions(user, order);

        Review review = Review.createReview(requestDto.getContent(), requestDto.getRating(), user, order);
        reviewRepository.save(review);
        return ReviewResponseDto.from(review);
    }


    /* 리뷰 작성 가능 여부 검증 */
    private void validateReviewPermissions(User user, Orders order) {
        // 주문의 구매자인 경우에만 리뷰 허용
        if (!order.getBuyerUser().equals(user)) {
            throw new CustomException(ErrorCode.REVIEW_ACCESS_DENIED);
        }

        // 구매확정 상태인 경우에만 리뷰 허용
        if (order.getOrderState() != OrderState.구매확정) {
            throw new CustomException(ErrorCode.INVALID_REVIEW_STATE);
        }

        // 하나의 주문에 리뷰 중복 작성 불가
        if (reviewRepository.existsByOrderId(order.getOrderId())) {
            throw new CustomException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }
    }

    public ReviewDetailDto getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        return ReviewDetailDto.from(review);
    }

    /* 상품별 리뷰 목록 조회 */
    public List<ReviewDetailDto> getProductReviews(Long productId, String sort) {
        List<Review> reviews;

        switch (sort.toLowerCase()) {
            case "rating":
                reviews = reviewRepository.findByProductIdOrderByRatingDesc(productId);
                break;
            case "oldest":
                reviews = reviewRepository.findByProductIdOrderByCreatedAtAsc(productId);
                break;
            case "latest":
                reviews = reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
                break;
            default:
                reviews = reviewRepository.findByProductId(productId);
                break;
        }
        return reviews.stream()
                .map(ReviewDetailDto::from)
                .collect(Collectors.toList());
    }
    /* 리뷰 삭제 */
    public void delete(User user, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().equals(user)) {
            throw new CustomException(ErrorCode.REVIEW_ACCESS_DENIED);
        }

        reviewRepository.delete(review);
    }
}
