package com.notification.api.core.socket.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Component
@RequiredArgsConstructor
public class StompChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("message: {}", message);

        // 적당한 StompCommand에 따른 기능 구현

        // 연결시에만 Header를 요구하고 검증한다.
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            log.info("헤더 : {}", message.getHeaders());
            log.info("토큰 : {}", accessor.getNativeHeader("Authorization"));
            log.info("Validate token...");

            // String token = String.valueOf(accessor.getNativeHeader("Authorization"));
            // AppUserDetails user =  JWTUtil.verifyToken(token);

            log.info("사용자 {} 가 연결되었습니다.", "TODO");
        }
        return message;
    }
}