package com.notification.api.core.socket.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Slf4j
@Component
public class WebSocketEventListener {

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        if (log.isDebugEnabled()) {
            log.info("Connected ::: {}", event.getUser());
        }
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        if (log.isDebugEnabled()) {
            log.info("Disconnected ::: {}", event.getUser());
        }
    }

    @EventListener
    public void handleSessionSubscribe(SessionSubscribeEvent event) {
        if (log.isDebugEnabled()) {
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
            log.info("Subscribed ::: {}, Target {}", event.getUser(), accessor.getDestination());
        }
    }

    @EventListener
    public void handleSessionUnsubscribe(SessionUnsubscribeEvent event) {
        if (log.isDebugEnabled()) {
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
            log.info("Subscribed ::: {}, Target {}", event.getUser(), accessor.getDestination());
        }
    }
}
