package com.chatting.domain.chat.presentation.dto;

public class MessageRequest {
    public record MessageReq(
            Long roomId,
            String message
    ) {
        public boolean vaild() {
            if(roomId == null) return false;
            if(message.isEmpty()) return false;
            return true;
        }
    }
}
