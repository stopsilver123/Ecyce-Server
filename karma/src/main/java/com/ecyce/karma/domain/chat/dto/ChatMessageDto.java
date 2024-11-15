package com.ecyce.karma.domain.chat.dto;

import com.ecyce.karma.domain.chat.entity.ChatMessage;
import com.ecyce.karma.domain.chat.entity.ChatRoom;
import com.ecyce.karma.domain.chat.entity.MessageType;
import com.ecyce.karma.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    private Long chatId;
    private Long roomId;
    private String sender;
    private String content;
    private MessageType type;

    // DTO를 엔티티로 변환
    public static ChatMessage to(ChatMessageDto dto, ChatRoom chatRoom, User sender) {
        return ChatMessage.builder()
                .type(dto.getType())
                .chatRoom(chatRoom)
                .sender(sender)
                .content(dto.getContent())
                .timestamp(LocalDateTime.now())
                .build();
    }

    // 엔티티를 DTO로 변환
    public static ChatMessageDto from(ChatMessage chatMessage) {
        return ChatMessageDto.builder()
                .chatId(chatMessage.getChatId())
                .roomId(chatMessage.getChatRoom().getRoomId())
                .sender(chatMessage.getSender().getNickname())
                .content(chatMessage.getContent())
                .type(chatMessage.getType())
                .build();
    }
}
