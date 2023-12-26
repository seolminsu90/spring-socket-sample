package com.notification.api.common.exception;

import com.notification.api.core.socket.domain.SocketPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final SimpMessagingTemplate messagingTemplate;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        e.printStackTrace();
        return "error";
    }

    @MessageExceptionHandler(Exception.class)
    public void handleWebSocketException(Exception e, SimpMessageHeaderAccessor headerAccessor, Principal principal) {
        if (principal != null) {
            log.error("Error ::: {} - {}", e.getMessage(), principal.getName());

            SocketPayload payload = new SocketPayload(StatusCode.FAIL, "시스템", e.getMessage());
            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/errors", payload);
        } else {
            log.error("Error :: {}", e.getMessage());
        }
    }
}
