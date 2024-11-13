package com.ecyce.karma.domain.chat.repository;

import com.ecyce.karma.domain.chat.entity.ChatRoom;
import com.ecyce.karma.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByBuyerOrSeller(User buyer, User seller);
    List<ChatRoom> findByBuyer(User buyer);
    List<ChatRoom> findBySeller(User seller);
}
