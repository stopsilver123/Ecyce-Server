package com.ecyce.karma.domain.chat.service;

import com.ecyce.karma.domain.chat.dto.ChatRoomDetailDto;
import com.ecyce.karma.domain.chat.dto.ChatRoomListDto;
import com.ecyce.karma.domain.chat.dto.ChatRoomResponseDto;
import com.ecyce.karma.domain.chat.entity.ChatMessage;
import com.ecyce.karma.domain.chat.entity.ChatRoom;
import com.ecyce.karma.domain.chat.repository.ChatMessageRepository;
import com.ecyce.karma.domain.chat.repository.ChatRoomRepository;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    /* 채팅방 생성 */
    public ChatRoomResponseDto createChatRoom(User creator, String otherUserName, boolean isOtherUserBuyer) {
        User otherUser = userRepository.findByNickname(otherUserName)
                .orElseThrow(() -> new IllegalArgumentException("INVALID_OTHER_USER_NAME"));

        ChatRoom chatRoom = setupChatRoomWithRoles(creator, otherUser, isOtherUserBuyer);
        chatRoomRepository.save(chatRoom);

        return ChatRoomResponseDto.from(chatRoom);
    }

    /* 전체 채팅방 목록: 사용자가 buyer 또는 seller로 참여한 채팅방 */
    public List<ChatRoomListDto> findAllChatRooms(User user) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByBuyerOrSeller(user, user);
        return chatRooms.stream()
                .map(this::toChatRoomListDto)
                .collect(Collectors.toList());
    }

    /* 판매 채팅방 목록: 사용자가 buyer로 참여한 채팅방 */
    public List<ChatRoomListDto> findSellingChatRooms(User user) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByBuyer(user);
        return chatRooms.stream()
                .map(this::toChatRoomListDto)
                .collect(Collectors.toList());
    }

    /* 구매 채팅방 목록: 사용자가 seller로 참여한 채팅방 */
    public List<ChatRoomListDto> findBuyingChatRooms(User user) {
        List<ChatRoom> chatRooms = chatRoomRepository.findBySeller(user);
        return chatRooms.stream()
                .map(this::toChatRoomListDto)
                .collect(Collectors.toList());
    }

    /* 특정 채팅방의 상세 정보 조회 */
    public List<ChatRoomDetailDto> findChatRoomDetails(Long roomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("INVALID_ROOM_ID"));
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomOrderByTimestampAsc(chatRoom);
        return messages.stream()
                .map(ChatRoomDetailDto::from)
                .collect(Collectors.toList());
    }

    // ChatRoom을 ChatRoomListDto로 변환
    private ChatRoomListDto toChatRoomListDto(ChatRoom chatRoom) {
        ChatMessage latestMessage = chatMessageRepository.findTopByChatRoomOrderByTimestampDesc(chatRoom);
        return ChatRoomListDto.from(chatRoom, latestMessage);
    }

    // creator가 buyer인지 seller인지 판단
    private ChatRoom setupChatRoomWithRoles(User creator, User otherUser, boolean isOtherUserBuyer) {
        User buyer = isOtherUserBuyer ? otherUser : creator;
        User seller = isOtherUserBuyer ? creator : otherUser;

        return ChatRoom.builder()
                .buyer(buyer)
                .seller(seller)
                .name(otherUser.getNickname())
                .build();
    }
}