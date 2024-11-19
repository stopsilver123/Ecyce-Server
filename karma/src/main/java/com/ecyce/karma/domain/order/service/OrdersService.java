package com.ecyce.karma.domain.order.service;

import com.ecyce.karma.domain.order.dto.OrderCreateRequestDto;
import com.ecyce.karma.domain.order.dto.OrderCreateResponseDto;
import com.ecyce.karma.domain.order.dto.OrderOverviewResponseDto;
import com.ecyce.karma.domain.order.dto.OrderResponseDto;
import com.ecyce.karma.domain.order.entity.Orders;
import com.ecyce.karma.domain.order.repository.OrdersRepository;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductOption;
import com.ecyce.karma.domain.product.repository.ProductOptionRepository;
import com.ecyce.karma.domain.product.repository.ProductRepository;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.domain.user.repository.UserRepository;
import com.ecyce.karma.global.exception.CustomException;
import com.ecyce.karma.global.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersService {
    private final OrdersRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    // 주문 생성
    @Transactional
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto, User buyer) {
        Product product = productRepository.findById(requestDto.getProductId())
                                           .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        ProductOption productOption = productOptionRepository.findById(requestDto.getProductOptionId())
                                                       .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_OPTION_NOT_FOUND));
        User seller = product.getUser();
        Orders order = Orders.createOrder(requestDto.getRequest(), seller, buyer, product, productOption,
                requestDto.getOrderCount());

        Orders savedOrder = orderRepository.save(order);

        return OrderCreateResponseDto.from(savedOrder);
    }

    // 주문 단건 조회 (판매자, 구매자 동시 접근 가능)
    public OrderResponseDto getOrderById(Long orderId, User user) {
        // 주문 존재 여부 확인
        Orders order = orderRepository.findByOrderId(orderId)
                                      .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        // 권한 확인
        if (!order.getBuyerUser().getUserId().equals(user.getUserId()) &&
                !order.getSellerUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(ErrorCode.ORDER_ACCESS_DENIED);
        }

        return OrderResponseDto.from(order);
    }

    // 구매 내역 조회 (특정 구매자 기준)
    public List<OrderOverviewResponseDto> getAllOrdersByBuyer(User user) {
        List<Orders> orders = orderRepository.findAllByBuyerUserId(user.getUserId());
//        if (orders.isEmpty()) {
//            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
//        }
        return orders.stream()
                     .map(OrderOverviewResponseDto::fromEntity)
                     .collect(Collectors.toList());
    }

    // 판매 내역 조회 (특정 판매자 기준)
    public List<OrderOverviewResponseDto> getAllOrdersBySeller(User user) {
        List<Orders> orders = orderRepository.findAllBySellerUserId(user.getUserId());
//        if (orders.isEmpty()) {
//            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
//        }
        return orders.stream()
                     .map(OrderOverviewResponseDto::fromEntity)
                     .collect(Collectors.toList());
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId, Long buyerID) {
        Orders order = orderRepository.findByOrderIdAndUserId(orderId, buyerID)
                                      .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        // 권한 확인
        if (!order.getBuyerUser().getUserId().equals(buyerID)) {
            throw new CustomException(ErrorCode.ORDER_ACCESS_DENIED);
        }

        // 주문 상태를 취소로 변경
        try {
            order.cancelOrder();
        } catch (IllegalStateException e) {
            throw new CustomException(ErrorCode.ORDER_CANNOT_BE_CANCELED);
        }
    }
}
