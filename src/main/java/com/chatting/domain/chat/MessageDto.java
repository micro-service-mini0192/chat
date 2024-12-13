package com.chatting.domain.chat;

public class MessageDto {
    public record Message(
        String id,
        String message
    ) {}
}
