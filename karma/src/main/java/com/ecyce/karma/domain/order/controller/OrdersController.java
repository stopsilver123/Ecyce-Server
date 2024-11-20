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
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId, @AuthUser User user) {
        OrderResponseDto orderResponseDto = ordersService.getOrderById(orderId, user);
        return ResponseEntity.ok(orderResponseDto);
    }

    // 구매 내역 전체 조회
    @GetMapping("/buyer")
    public ResponseEntity<List<OrderOverviewResponseDto>> getBuyerOrders(@AuthUser User user) {
        List<OrderOverviewResponseDto> orders = ordersService.getAllOrdersByBuyer(user);
        return ResponseEntity.ok(orders);
    }

    // 판매 내역 전체 조회
    @GetMapping("/seller")
    public ResponseEntity<List<OrderOverviewResponseDto>> getSellerOrders(@AuthUser User user) {
        List<OrderOverviewResponseDto> orders = ordersService.getAllOrdersBySeller(user);
        return ResponseEntity.ok(orders);
    }

    // 판매자의 주문 수락 또는 거절
    @PatchMapping("/{orderId}/accept")
    public ResponseEntity<String> acceptOrder(
            @PathVariable Long orderId,
            @RequestParam boolean accepted,
            @AuthUser User seller
    ) {
        ordersService.acceptOrRejectOrder(orderId, seller, accepted);
        return ResponseEntity.ok(accepted ? "주문이 성공적으로 수락되었습니다." : "주문이 성공적으로 거절되었습니다.");
    }

    // 제작 시작
    @PatchMapping("/{orderId}/start")
    public ResponseEntity<String> startProduction(@PathVariable Long orderId, @AuthUser User seller) {
        ordersService.startProduction(orderId, seller);
        return ResponseEntity.ok("제작이 시작되었습니다.");
    }

    // 제작 완료
    @PatchMapping("/{orderId}/complete")
    public ResponseEntity<String> completeProduction(@PathVariable Long orderId, @AuthUser User seller) {
        ordersService.completeProduction(orderId, seller);
        return ResponseEntity.ok("제작이 완료되었습니다.");
    }

    // 배송 시작
    @PatchMapping("/{orderId}/ship")
    public ResponseEntity<String> startShipping(
            @PathVariable Long orderId,
            @RequestParam String invoiceNumber,
            @AuthUser User seller
    ) {
        ordersService.startShipping(orderId, seller, invoiceNumber);
        return ResponseEntity.ok("배송이 시작되었습니다.");
    }

    // 구매 확정
    @PatchMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirmOrder(@PathVariable Long orderId, @AuthUser User buyer) {
        ordersService.confirmOrder(orderId, buyer);
        return ResponseEntity.ok("구매가 확정되었습니다.");
    }

    // 주문 취소
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId, @AuthUser User user) {
        ordersService.cancelOrder(orderId, user.getUserId());
        return ResponseEntity.ok("주문이 성공적으로 취소되었습니다.");
    }
}
