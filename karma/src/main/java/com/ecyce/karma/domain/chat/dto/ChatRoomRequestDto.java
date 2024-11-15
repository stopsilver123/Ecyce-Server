package com.ecyce.karma.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRequestDto {
    private String otherUserName; // 상대방의 닉네임
    private boolean isOtherUserBuyer; // 상대가 buyer인지 여부
}
