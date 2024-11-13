package com.ecyce.karma.domain.chat.dto;

import com.ecyce.karma.domain.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomResponseDto {
    private Long roomId;
    private String name;
    private String buyer;
    private String seller;

    public static ChatRoomResponseDto from(ChatRoom chatRoom) {
        return new ChatRoomResponseDto(
                chatRoom.getRoomId(),
                chatRoom.getName(),
                chatRoom.getBuyer().getNickname(),
                chatRoom.getSeller().getNickname()
        );
    }
}
