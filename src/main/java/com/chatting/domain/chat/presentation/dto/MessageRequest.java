package com.chatting.domain.chat.presentation.dto;

import lombok.Builder;

public class MessageRequest {
    public record MessageReq(
            Long roomId,
            String message
    ) {}
}
