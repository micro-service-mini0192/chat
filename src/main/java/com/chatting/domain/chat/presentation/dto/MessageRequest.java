package com.chatting.domain.chat.presentation.dto;

import lombok.Builder;

public class MessageRequest {
    @Builder
    public record MessageReq(
            Long id,
            String message
    ) {}
}
