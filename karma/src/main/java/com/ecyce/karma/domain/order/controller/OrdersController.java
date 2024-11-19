package com.ecyce.karma.domain.order.controller;

import com.ecyce.karma.domain.auth.customAnnotation.AuthUser;
import com.ecyce.karma.domain.order.dto.OrderCreateRequestDto;
import com.ecyce.karma.domain.order.dto.OrderCreateResponseDto;
import com.ecyce.karma.domain.order.dto.OrderOverviewResponseDto;
import com.ecyce.karma.domain.order.dto.OrderResponseDto;
import com.ecyce.karma.domain.order.service.OrdersService;
import com.ecyce.karma.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderCreateResponseDto> createOrder(@RequestBody OrderCreateRequestDto requestDto, @AuthUser User user) {
        OrderCreateResponseDto responseDto = ordersService.createOrder(requestDto, user);
        return ResponseEntity.ok(responseDto);
    }

    // 주문 단건 조회
    // TODO : 조회에 AuthUser 추가 필요
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) {
        OrderResponseDto orderResponseDto = ordersService.getOrderById(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }

    // 주문 전체 조회
    @GetMapping
    public ResponseEntity<List<OrderOverviewResponseDto>> getAllOrders() {
        List<OrderOverviewResponseDto> orderList = ordersService.getAllOrders();
        return ResponseEntity.ok(orderList);
    }

    // 주문 취소
    @PatchMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId, @AuthUser User user) {
        ordersService.cancelOrder(orderId, user);
        return ResponseEntity.ok("주문이 성공적으로 취소되었습니다.");
    }
}
