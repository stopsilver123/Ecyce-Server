package com.ecyce.karma.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //://localhost/ws/chat을 호출하면 웹 소켓 연결됨
        registry.addEndpoint("/ws") // /ws으로 들어오는 경우
                .setAllowedOrigins("https://jiangxy.github.io", "http://localhost:3000")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //메모리 기반 메시지 브로커가 해당 api 구독 클라이언트에게 메시지 전달
        registry.enableSimpleBroker("/sub"); //구독 요청(어디서 받는지)
        registry.setApplicationDestinationPrefixes("/pub"); //메시지 발행 시 사용(어디로 보낼지)
    }
}