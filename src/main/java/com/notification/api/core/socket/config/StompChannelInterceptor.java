package com.notification.api.core.socket.config;

import com.notification.api.common.domain.AppUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Component
@RequiredArgsConstructor
public class StompChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor
                .getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && accessor.getCommand() != null) {
            log.info("Message Type : {}", accessor.getCommand());
            switch (accessor.getCommand()) {
                case CONNECT:
                    stompConnectCommand(accessor);
                    break;
                case SUBSCRIBE:
                    stompSubscribeCommand(accessor);

                    // 기타 Command Option 추가
            }
        }

        return message;
    }

    private void stompConnectCommand(StompHeaderAccessor accessor) {
        List<String> authorizations = accessor.getNativeHeader("Authorization");
        if (authorizations == null || authorizations.isEmpty()) {
            throw new MessageDeliveryException("Authorization is not found..");
        }

        String token = authorizations.get(0);

        // TODO name => token 변경 및 token 검증, 추출 작업 전환 필요
        // AppUserDetails user =  JWTUtil.verifyToken(token);

        // TODO remove => 임시로 token == name으로 처리한다.
        AppUserDetails user = new AppUserDetails();
        user.setUsername(token);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null);
        accessor.setUser(auth);

        log.info("사용자 {} 가 연결되었습니다.", token);
    }

    private void stompSubscribeCommand(StompHeaderAccessor accessor) {
        // TODO 구독 제한된 채널의 경우 유저 정보나 권한을 확인하고 제어해준다.

        String destination = accessor.getDestination();
        log.info("사용자 {} 가 {} 채널을 구독합니다.", accessor.getUser().getName(), destination);
    }
}