package com.ecyce.karma.domain.chat.dto;

import com.ecyce.karma.domain.chat.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatRoomDetailDto {
    private Long messageId;
    private String sender;
    private String content;
    private LocalDateTime timestamp;

    public static ChatRoomDetailDto from(ChatMessage chatMessage) {
        return new ChatRoomDetailDto(
                chatMessage.getChatId(),
                chatMessage.getSender().getNickname(),
                chatMessage.getContent(),
                chatMessage.getTimestamp()
        );
    }
}
