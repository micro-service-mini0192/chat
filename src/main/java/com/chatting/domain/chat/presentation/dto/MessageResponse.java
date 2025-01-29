package com.chatting.domain.chat.presentation.dto;

import lombok.Builder;

public class MessageResponse {
    @Builder
    public record MessageRes(
            Long roomId,
            String message
    ) {
        public static MessageRes toDto(Long roomId, String message) {
            return MessageRes.builder()
                    .roomId(roomId)
                    .message(message)
                    .build();
        }
    }
}
