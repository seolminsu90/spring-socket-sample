package com.notification.api.core.socket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompChannelInterceptor stompChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket을 이용하는 URL
        registry
                .addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
        // SockJS을 이용하는 URL
        registry
                .addEndpoint("/ws/sockjs")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic", "/user"); // 구독 Prefix
        registry.setUserDestinationPrefix("/user");
        registry.setApplicationDestinationPrefixes("/pub"); // App에서 메시지 가공을 위한 Endpoint Prefix

//      외부 브로커 사용시
//        registry
//                .setApplicationDestinationPrefixes("/pub")
//                .enableStompBrokerRelay("/token");
//                .setRelayPort(61613)
//                .setClientLogin(username)
//                .setClientPasscode(password);
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        ByteArrayMessageConverter byteArrayMessageConverter = new ByteArrayMessageConverter();
        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
        messageConverters.add(byteArrayMessageConverter);
        messageConverters.add(mappingJackson2MessageConverter);
        return false;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompChannelInterceptor);
    }

}