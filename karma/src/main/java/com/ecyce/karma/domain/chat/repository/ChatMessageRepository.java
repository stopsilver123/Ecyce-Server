package com.ecyce.karma.domain.chat.repository;

import com.ecyce.karma.domain.chat.entity.ChatMessage;
import com.ecyce.karma.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomRoomId(Long roomId);
    List<ChatMessage> findByChatRoomOrderByTimestampAsc(ChatRoom chatRoom);
    ChatMessage findTopByChatRoomOrderByTimestampDesc(ChatRoom chatRoom);
}
