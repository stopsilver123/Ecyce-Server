package com.ecyce.karma.domain.chat.entity;

import com.ecyce.karma.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId; // 메시지 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type; // 메시지 타입 (CHAT, JOIN, LEAVE)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom chatRoom; // 연관된 채팅방

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender; // 메시지 보낸 사람

    @Column(nullable = false)
    private String content; // 메시지 내용

    private LocalDateTime timestamp; //메시지 전송 시간

}