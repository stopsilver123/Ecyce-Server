package com.ecyce.karma.domain.user.service;

import com.ecyce.karma.domain.order.entity.Orders;
import com.ecyce.karma.domain.order.repository.OrdersRepository;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.repository.ProductRepository;
import com.ecyce.karma.domain.review.entity.Review;
import com.ecyce.karma.domain.review.repository.ReviewRepository;
import com.ecyce.karma.domain.user.dto.response.ArtistInfoResponse;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final ReviewRepository reviewRepository;

    /* 작가 정보 반환 */
    public ArtistInfoResponse getArtistInfo(Long userId) {

        User user = userRepository.findByUserId(userId);
        float averageRating = calAverageRating(userId);

        return ArtistInfoResponse.from(user , averageRating);
    }

    /* 작가의 작품 평균 리뷰 수 계산 */
    private float calAverageRating(Long userId){
        int sum =0;
        int count = 0;
        List<Product> productList = productRepository.findByUserId(userId);

        List<Orders> ordersList = productList.stream()
                .flatMap(product -> ordersRepository.findByProductId(product.getProductId()).stream())
                .collect(Collectors.toList());

        for (Orders order : ordersList) {
            List<Review> reviews = reviewRepository.findByOrderId(order.getOrderId());
            for (Review review : reviews) {
                sum += review.getRating(); // review 별로 점수 합산
                count++;
            }
        }
        
        // 계산
        float averageRating = count > 0 ? (float) sum / count : 0.0f;

        return averageRating;
    }

}