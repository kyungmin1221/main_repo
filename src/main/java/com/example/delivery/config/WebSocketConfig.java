package com.example.delivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // "/topic" prefix를 사용하여 클라이언트로 메시지를 전달합니다.
        config.enableSimpleBroker("/topic"); // 이 prefix가 붙은 주소로 메시지를 전달 받을 수 있다.
        // config.setApplicationDestinationPrefixes("/app"); // 클라이언트로부터 메시지를 받음
    }

    // Client에서 websocket연결할 때 사용할 API 경로를 설정해주는 메서드.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // "/websocket" endpoint를 등록하고, SockJS를 사용하여 연결합니다.
        registry.addEndpoint("/websocket").withSockJS(); // 웹 소켓에 연결할 때 필요한 주소
    }

    // 핸들러. 상황에 따라 필요한 경우 바로 브로커로 가는 것이 아니라, 메시지의 어떤 처리나 가공이 필요할 때 핸들러를 타게 할 수 있음
    /*@Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }*/
}
