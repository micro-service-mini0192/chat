package com.chatting;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        session.sendMessage(new TextMessage("연결 완료"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        session.sendMessage(new TextMessage("연결 해제"));
        session.close();
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for(Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
            if(entry.getValue().equals(session)) continue;
            entry.getValue().sendMessage(message);
        }
        session.sendMessage(new TextMessage("메시지 전송 완료"));
    }
}
