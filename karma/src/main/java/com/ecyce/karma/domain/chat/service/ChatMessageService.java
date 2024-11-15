package com.ecyce.karma.domain.chat.service;

import com.ecyce.karma.domain.chat.dto.ChatListResponseDto;
import com.ecyce.karma.domain.chat.dto.ChatMessageDto;
import com.ecyce.karma.domain.chat.entity.ChatMessage;
import com.ecyce.karma.domain.chat.entity.ChatRoom;
import com.ecyce.karma.domain.chat.repository.ChatMessageRepository;
import com.ecyce.karma.domain.chat.repository.ChatRoomRepository;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public ChatMessage saveMessage(ChatMessageDto dto) {
        ChatRoom chatRoom = chatRoomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("INVALID_ROOM_ID"));

        User sender = userRepository.findByNickname(dto.getSender())
                .orElseThrow(() -> new IllegalArgumentException("INVALID_SENDER_NAME " + dto.getSender()));

        return chatMessageRepository.save(ChatMessageDto.to(dto, chatRoom, sender));
    }

    @Transactional(readOnly = true)
    public ChatListResponseDto findMessagesByRoomId(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("INVALID_ROOM_ID"));

        List<ChatMessageDto> messageDtos = chatMessageRepository.findByChatRoomRoomId(roomId).stream()
                .map(ChatMessageDto::from)
                .collect(Collectors.toList());

        return ChatListResponseDto.of(chatRoom.getRoomId(), chatRoom.getName(), messageDtos);
    }
}
