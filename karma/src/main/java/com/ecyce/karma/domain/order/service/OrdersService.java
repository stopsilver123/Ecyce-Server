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
    private final UserRepository userRepository;

    // 주문 생성하기
    @Transactional
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto, User buyer) {
        Product product = productRepository.findById(requestDto.getProductId())
                                           .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        ProductOption productOption = productOptionRepository.findById(requestDto.getProductOptionId())
                                                       .orElseThrow(() -> new IllegalArgumentException("상품 옵션을 찾을 수 없습니다."));
        User seller = product.getUser();
        Orders order = Orders.createOrder(requestDto.getRequest(), seller, buyer, product, productOption,
                requestDto.getOrderCount());

        Orders savedOrder = orderRepository.save(order);

        return OrderCreateResponseDto.from(savedOrder);
    }

    // 주문 단건 조회
    public OrderResponseDto getOrderById(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                                      .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
        return OrderResponseDto.from(order);
    }

    // 주문 전체 조회
    public List<OrderOverviewResponseDto> getAllOrders() {
        // 1. 전체 주문 데이터 조회
        List<Orders> orders = orderRepository.findAll();

        // 2. OrderOverviewResponseDto로 변환하여 반환
        return orders.stream()
                     .map(OrderOverviewResponseDto::fromEntity) // fromEntity 메서드를 사용
                     .collect(Collectors.toList());
    }


    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId, User buyer) {
        Orders order = orderRepository.findById(orderId)
                                      .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        // 요청자가 구매자인지 확인
        if (!order.getBuyerUser().equals(buyer)) {
            throw new IllegalArgumentException("해당 주문의 취소 권한이 없습니다.");
        }

        // 주문 상태를 취소로 변경
        order.cancelOrder();
    }
}
