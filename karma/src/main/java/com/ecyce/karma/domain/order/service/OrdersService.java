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
@Transactional
public class OrdersService {
    private final OrdersRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    // 주문 생성
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<OrderOverviewResponseDto> getAllOrdersBySeller(User user) {
        List<Orders> orders = orderRepository.findAllBySellerUserId(user.getUserId());
//        if (orders.isEmpty()) {
//            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
//        }
        return orders.stream()
                     .map(OrderOverviewResponseDto::fromEntity)
                     .collect(Collectors.toList());
    }

    // 주문 수락 또는 거절
    public void acceptOrRejectOrder(Long orderId, User seller, boolean accepted) {
        Orders order = orderRepository.findByOrderId(orderId)
                                       .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        if (!order.getSellerUser().equals(seller)) {
            throw new CustomException(ErrorCode.ORDER_ACCESS_DENIED);
        }
        order.acceptOrReject(accepted);
    }

    // 제작 시작
    public void startProduction(Long orderId, User seller) {
        Orders order = orderRepository.findByOrderId(orderId)
                                       .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        if (!order.getSellerUser().equals(seller)) {
            throw new CustomException(ErrorCode.ORDER_ACCESS_DENIED);
        }
        order.startProduction();
    }

    // 제작 완료
    public void completeProduction(Long orderId, User seller) {
        Orders order = orderRepository.findByOrderId(orderId)
                                       .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        if (!order.getSellerUser().equals(seller)) {
            throw new CustomException(ErrorCode.ORDER_ACCESS_DENIED);
        }
        order.completeProduction();
    }

    // 배송 시작
    public void startShipping(Long orderId, User seller, String invoiceNumber) {
        Orders order = orderRepository.findByOrderId(orderId)
                                       .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        if (!order.getSellerUser().equals(seller)) {
            throw new CustomException(ErrorCode.ORDER_ACCESS_DENIED);
        }
        order.startShipping(invoiceNumber);
    }

    // 구매 확정
    public void confirmOrder(Long orderId, User buyer) {
        Orders order = orderRepository.findByOrderId(orderId)
                                       .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        if (!order.getBuyerUser().equals(buyer)) {
            throw new CustomException(ErrorCode.ORDER_ACCESS_DENIED);
        }
        order.confirmOrder();
    }

    // 주문 취소 (구매자, 판매자 모두)
    public void cancelOrder(Long orderId, Long userId) {
        Orders order = orderRepository.findByOrderId(orderId)
                                      .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if (!order.getBuyerUser().getUserId().equals(userId) && !order.getSellerUser().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.ORDER_ACCESS_DENIED);
        }

        order.cancelOrder();
    }
}
