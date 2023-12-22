package com.notification.api.core.socket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@Deprecated
@Slf4j
//@Configuration
//@EnableWebSocketSecurity
public class WebSocketSecurityConfig {

    // Http 요청을 처리하는 WebSecurity 처럼
    // Message endpoint마다 권한설정 할 수 있지만 잘 안됨. 연결 이후에는 꼭 필요한 기능이 아니므로 우선 PASS
    // 세부적인건 어짜피 DB나 데이터 구조로 처리할 수 있을 것 같다.

    // @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages.anyMessage().permitAll();
        return messages.build();
    }
}