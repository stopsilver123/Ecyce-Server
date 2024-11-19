package com.ecyce.karma.domain.user.service;

import com.ecyce.karma.domain.address.entity.Address;
import com.ecyce.karma.domain.order.entity.Orders;
import com.ecyce.karma.domain.order.repository.OrdersRepository;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.repository.ProductRepository;
import com.ecyce.karma.domain.review.entity.Review;
import com.ecyce.karma.domain.review.repository.ReviewRepository;
import com.ecyce.karma.domain.user.dto.request.ModifyInfoRequest;
import com.ecyce.karma.domain.user.dto.request.UserInfoRequest;
import com.ecyce.karma.domain.user.dto.response.AllUserInfo;
import com.ecyce.karma.domain.user.dto.response.ArtistInfoResponse;
import com.ecyce.karma.domain.user.dto.response.UserInfo;
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
    private final UserMapper userMapper;

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

    /* 사용자 정보 조회 */
    public UserInfo getUserInfo(User user) {
        return UserInfo.from(user);
    }


    /* 새로운 사용자인 경우 정보 저장 */
    public UserInfo saveNewUser(User user, UserInfoRequest dto)  {
        // 주소 먼저 update
        Address updateAddress =  Address.toEntity(user , dto);
        user.updateNewUserInfo(user , dto);
        userRepository.save(user);
        User updateUser = userRepository.findByUserId(user.getUserId());
        return UserInfo.from(updateUser);
    }

    /* 사용자 정보 수정 */
    public AllUserInfo modifyUserInfo(User user, ModifyInfoRequest request) {
        User targetUser = userRepository.findByUserId(user.getUserId());

        userMapper.updateUserFromDto(request, targetUser);
        log.info("바뀐 bio {}" ,targetUser.getBio());

        userRepository.save(targetUser);
        log.info("save 메서드 호출");

        User updateUser = userRepository.findByUserId(user.getUserId());
        return AllUserInfo.from(updateUser);
    }
}