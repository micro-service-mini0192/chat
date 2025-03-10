package com.chatting.domain.chat.presentation.dto;

import com.chatting.domain.members.domain.Member;
import lombok.Builder;

import java.time.LocalDateTime;

public class MessageResponse {
    @Builder
    public record MessageRes(
            Long roomId,
            String message,
            MemberInfo member,
            String createAt
    ) {
        public record MemberInfo(
                Long memberId,
                String nickname
        ) {}
        public static MessageRes toDto(Long roomId, String message, Member member) {
            MemberInfo memberInfo = new MemberInfo(member.getId(), member.getNickname());
            return MessageRes.builder()
                    .roomId(roomId)
                    .message(message)
                    .member(memberInfo)
                    .createAt(LocalDateTime.now().toString())
                    .build();
        }
    }

    @Builder
    public record KafkaMessageRes(
            MessageRes data,
            String sender
    ) {}
}
