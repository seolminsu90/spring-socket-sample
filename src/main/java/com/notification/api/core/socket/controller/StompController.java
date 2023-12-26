package com.notification.api.core.socket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class StompController {
    // /app으로 Prefix를 설정했기때문에 /app~ 으로 호출한다.
    // 채널은 /topic (1:N) /queue (1:1) 로 구현한다.
    private final SimpMessagingTemplate messagingTemplate;

    public StompController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    public void sendMessageToTopic(Message<?> message, MessageHeaders messageHeaders,
                                   StompHeaderAccessor stompHeaderAccessor, @Payload String payload,
                                   @Header("destination") String destination, @Headers Map<String, String> headers) {

        log.info("---- Message ---- {}", message);
        log.info("---- MessageHeaders ---- {}", messageHeaders);
        log.info("---- StompHeaderAccessor ---- {}", stompHeaderAccessor);
        log.info("---- @Payload ---- {}", payload);
        log.info("----  @Headers ---- {}", headers);
        log.info("---- Destination ---- {}", destination);

        this.messagingTemplate.convertAndSend("/topic/sample-topic", payload);
    }

    @MessageMapping("/send/{target}")
    public void sendMessageToUser(StompHeaderAccessor stompHeaderAccessor, @Payload String payload, @DestinationVariable String target) {
        log.info("command: {}\n session: {}", stompHeaderAccessor.getCommand(), stompHeaderAccessor.getSessionId());
        this.messagingTemplate.convertAndSend("/queue/" + target, payload);
    }
}
