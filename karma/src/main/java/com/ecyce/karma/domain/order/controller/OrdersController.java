package com.ecyce.karma.domain.order.controller;

import com.ecyce.karma.domain.order.dto.OrderCreateRequestDto;
import com.ecyce.karma.domain.order.dto.OrderCreateResponseDto;
import com.ecyce.karma.domain.order.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    // 주문 생성하기
    @PostMapping
    public ResponseEntity<OrderCreateResponseDto> createOrder(@RequestBody OrderCreateRequestDto requestDto, @RequestParam Long buyerId) {
        OrderCreateResponseDto responseDto = ordersService.createOrder(requestDto, buyerId);
        return ResponseEntity.ok(responseDto);
    }



}
