package com.notification.api.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        /*var principal = session.getPrincipal();

        if (principal == null || principal.getName() == null) {
            session.close(SERVER_ERROR.withReason("User must be authenticated"));
            return;
        }*/

        sessions.put(session.getId(), session);
        log.info("연결 성공 {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("받은 메시지: " + message.getPayload());

        String responseMessage = "서버에서 보낸 응답 메시지";
        session.sendMessage(new TextMessage(responseMessage));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        //var principal = session.getPrincipal();
        sessions.remove(session.getId());
        log.info("연결 종료 {}", session.getId());
    }

}
