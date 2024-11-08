package com.ecyce.karma.domain.order.controller;

import com.ecyce.karma.domain.order.dto.OrderCreateRequestDto;
import com.ecyce.karma.domain.order.dto.OrderCreateResponseDto;
import com.ecyce.karma.domain.order.dto.OrderReadResponseDto;
import com.ecyce.karma.domain.order.service.OrdersService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderCreateResponseDto> createOrder(@RequestBody OrderCreateRequestDto requestDto, @RequestParam Long buyerId) {
        OrderCreateResponseDto responseDto = ordersService.createOrder(requestDto, buyerId);
        return ResponseEntity.ok(responseDto);
    }

    // 주문 단건 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderReadResponseDto> getOrder(@PathVariable Long orderId) {
        OrderReadResponseDto orderResponseDto = ordersService.getOrderById(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }

    // 주문 전체 조회
    @GetMapping
    public ResponseEntity<List<OrderReadResponseDto>> getAllOrders() {
        List<OrderReadResponseDto> orderList = ordersService.getAllOrders();
        return ResponseEntity.ok(orderList);
    }
}
