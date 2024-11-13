package com.ecyce.karma.domain.chat.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatListResponseDto {
    private Long roomId;
    private String roomName;
    private List<ChatMessageDto> messages;

    public static ChatListResponseDto of(Long roomId, String roomName, List<ChatMessageDto> messages) {
        return ChatListResponseDto.builder()
                .roomId(roomId)
                .roomName(roomName)
                .messages(messages)
                .build();
    }
}
