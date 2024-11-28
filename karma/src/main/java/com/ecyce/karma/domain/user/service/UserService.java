package com.ecyce.karma.domain.user.service;

import com.ecyce.karma.domain.address.entity.Address;
import com.ecyce.karma.domain.address.repository.AddressRepository;
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
import com.ecyce.karma.global.exception.CustomException;
import com.ecyce.karma.global.exception.ErrorCode;
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

    /* 사용자 정보 조회 */
    public UserInfo getUserInfo(User user) {
        return UserInfo.from(user);
    }


    /* 새로운 사용자인 경우 정보 저장 */
    public UserInfo saveNewUser(User user, UserInfoRequest dto)  {
        // 주소 먼저 update
        Address updateAddress =  Address.toEntity(user , dto);
        addressRepository.save(updateAddress);
        user.updateNewUserInfo(user , dto);
        userRepository.save(user);
        User updateUser = userRepository.findByUserId(user.getUserId());
        return UserInfo.from(updateUser , updateAddress);
    }

    /* 사용자 정보 수정 */
    public AllUserInfo modifyUserInfo(User user, ModifyInfoRequest request) {
        User targetUser = userRepository.findByUserId(user.getUserId());

        targetUser.updateUserInfo(request);

        userRepository.save(targetUser); // 기존값 유지하도록 수정해야함

        User updateUser = userRepository.findByUserId(user.getUserId());
        return AllUserInfo.from(updateUser);
    }

//    public AllUserInfo modifyUserInfo(User user, ModifyInfoRequest request) {
//        // 데이터베이스에서 기존 사용자 정보 조회
//        User targetUser = userRepository.findByUserId(user.getUserId());
//
//        // MapStruct를 사용해 요청 값을 기존 사용자 객체에 업데이트
//        userMapper.update(request, targetUser);
//
//        // 업데이트된 사용자 정보 저장
//        userRepository.save(targetUser);
//
//        // 갱신된 사용자 정보를 반환
//        return AllUserInfo.from(targetUser);
//    }

    /* 사용자 주소 수정 */
    public UserInfo modifyAddress(User user, ModifyAddressRequest request) {

        Address address =  addressRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));

      address.updateAddress(request);

      UserInfo userInfo = UserInfo.from(user);
      return userInfo;  // 이것도 기존값 넣어줘여겠다
    }
}