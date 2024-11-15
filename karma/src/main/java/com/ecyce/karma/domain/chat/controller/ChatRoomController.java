package com.ecyce.karma.domain.chat.controller;

import com.ecyce.karma.domain.auth.customAnnotation.AuthUser;
import com.ecyce.karma.domain.chat.dto.ChatRoomDetailDto;
import com.ecyce.karma.domain.chat.dto.ChatRoomListDto;
import com.ecyce.karma.domain.chat.dto.ChatRoomRequestDto;
import com.ecyce.karma.domain.chat.dto.ChatRoomResponseDto;
import com.ecyce.karma.domain.chat.service.ChatRoomService;
import com.ecyce.karma.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    /* 채팅방 생성 */
    @PostMapping("/room")
    public ResponseEntity<ChatRoomResponseDto> createRoom(@AuthUser User creator, @RequestBody ChatRoomRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomService.createChatRoom(creator, requestDto.getOtherUserName(), requestDto.getIsOtherUserBuyer()));
    }

    /* 전체 채팅 목록 조회 */
    @GetMapping("/rooms/all")
    public ResponseEntity<List<ChatRoomListDto>> getAllChatRooms(@AuthUser User user) {
        return ResponseEntity.ok(chatRoomService.findAllChatRooms(user));
    }

    /* 판매 채팅 목록 조회 */
    @GetMapping("/rooms/selling")
    public ResponseEntity<List<ChatRoomListDto>> getSellingChatRooms(@AuthUser User user) {
        return ResponseEntity.ok(chatRoomService.findSellingChatRooms(user));
    }

    /* 구매 채팅 목록 조회 */
    @GetMapping("/rooms/buying")
    public ResponseEntity<List<ChatRoomListDto>> getBuyingChatRooms(@AuthUser User user) {
        return ResponseEntity.ok(chatRoomService.findBuyingChatRooms(user));
    }

    /* 채팅 상세 정보 조회 */
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ChatRoomDetailDto>> getChatRoomDetails(@PathVariable (name = "roomId") Long roomId, @AuthUser User user) {
        return ResponseEntity.ok(chatRoomService.findChatRoomDetails(roomId, user));
    }
}