package com.ecyce.karma.domain.order.service;

import com.ecyce.karma.domain.order.dto.OrderCreateRequestDto;
import com.ecyce.karma.domain.order.dto.OrderCreateResponseDto;
import com.ecyce.karma.domain.order.entity.Orders;
import com.ecyce.karma.domain.order.repository.OrdersRepository;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductOption;
import com.ecyce.karma.domain.product.repository.ProductOptionRepository;
import com.ecyce.karma.domain.product.repository.ProductRepository;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.domain.user.repository.UserRepository;
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
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto, Long buyerId) {
        Product product = productRepository.findById(requestDto.getProductId())
                                           .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        //TODO 추후 상품과 옵션이 일치하는지 확인 필요.
        ProductOption productOption = productOptionRepository.findById(requestDto.getProductOptionId())
                                                       .orElseThrow(() -> new IllegalArgumentException("상품 옵션을 찾을 수 없습니다."));
        User buyer = userRepository.findById(buyerId)
                                   .orElseThrow(() -> new IllegalArgumentException("구매자를 찾을 수 없습니다."));
        User seller = product.getUser();

        Orders order = Orders.createOrder(requestDto.getRequest(), seller, buyer, product, productOption,
                requestDto.getOrderCount());

        Orders savedOrder = orderRepository.save(order);

        return OrderCreateResponseDto.from(savedOrder);
    }
}
