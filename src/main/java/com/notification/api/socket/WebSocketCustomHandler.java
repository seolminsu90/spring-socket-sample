package com.notification.api.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class WebSocketCustomHandler extends AbstractWebSocketHandler {

    private final Map<String, WebSocketSession> sessions;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        /*var principal = session.getPrincipal();

        if (principal == null || principal.getName() == null) {
            session.close(SERVER_ERROR.withReason("User must be authenticated"));
            return;
        }*/


        boolean isValid = verifySession(session);
        if (isValid) {
            // 인증 성공 시 연결을 허용합니다.
            log.info("연결 성공 {}", session.getId());
            super.afterConnectionEstablished(session);
            sessions.put(session.getId(), session);
        } else {
            // 인증 실패 시 연결을 닫습니다.
            log.info("연결 실패 {}", session.getId());
            session.close();
        }

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("받은 메시지: " + message.getPayload());

        String responseMessage = "서버에서 보낸 응답 메시지";
        session.sendMessage(new TextMessage(responseMessage));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        boolean isClosed = closeSession(session);
        if (isClosed) {
            sessions.remove(session.getId());
            log.info("연결 종료 {} - 이유: {}, 상태 코드: {}", session.getId(), status.getReason(), status.getCode());
        }
    }

    private boolean verifySession(WebSocketSession session) {
        String token = String.valueOf(session.getAttributes().get("token"));

        // JWT 검증


        return true;
    }

    private boolean closeSession(WebSocketSession session) {
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (Exception e) {
            log.error("WebSocket 세션 종료 중 오류 발생", e);
            return false;
        }
        return true;
    }

}
