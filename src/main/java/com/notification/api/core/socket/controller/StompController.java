package com.notification.api.core.socket.controller;

import com.notification.api.core.socket.domain.SocketPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StompController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    public void sendMessageToTopic(Message<SocketPayload> message, MessageHeaders messageHeaders,
                                   StompHeaderAccessor stompHeaderAccessor, @Payload SocketPayload payload,
                                   @Header("destination") String destination, @Headers Map<String, String> headers) {

        log.info("---- Message ---- {}", message);
        log.info("---- MessageHeaders ---- {}", messageHeaders);
        log.info("---- StompHeaderAccessor ---- {}", stompHeaderAccessor);
        log.info("---- @Payload ---- {}", payload);
        log.info("----  @Headers ---- {}", headers);
        log.info("---- Destination ---- {}", destination);
        log.info("---- principal ---- {}", stompHeaderAccessor.getUser());
        log.info("---- principal Name ---- {}", stompHeaderAccessor.getUser().getName());

        messagingTemplate.convertAndSend("/topic/notification-topic", payload);
    }

    @MessageMapping("/send/{target}")
    public void sendMessageToUser(StompHeaderAccessor stompHeaderAccessor, @Payload SocketPayload payload, @DestinationVariable(value = "target") String target) {
        log.info("command: {}, session: {}", stompHeaderAccessor.getCommand(), stompHeaderAccessor.getSessionId());
        log.info("target: {}, payload: {} ", target, payload);

        messagingTemplate.convertAndSendToUser(target, "/queue/dm", payload);
        // TIP. convertAndSendToUser에 의하여 /user/{username}/queue/dm 형태로 알아서 바뀌게 된다.
        // TIP. /queue/{target} 와같이 설정 후 messagingTemplate.convertAndSend("/queue/{target}", payload); 해도 동일하게 동작한다. (이때는 구독도 /queue/{target}로 하면 된다.
    }
}
