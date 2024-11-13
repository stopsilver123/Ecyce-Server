package com.ecyce.karma.domain.chat.dto;

import com.ecyce.karma.domain.chat.entity.ChatMessage;
import com.ecyce.karma.domain.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomListDto {
    private Long roomId;
    private String roomName;
    private String latestMessage;

    public static ChatRoomListDto from(ChatRoom chatRoom, ChatMessage latestMessage) {
        String content = (latestMessage != null) ? latestMessage.getContent() : "채팅 내역이 존재하지 않습니다. 채팅을 시작해보세요!";
        return new ChatRoomListDto(chatRoom.getRoomId(), chatRoom.getName(), content);
    }
}
