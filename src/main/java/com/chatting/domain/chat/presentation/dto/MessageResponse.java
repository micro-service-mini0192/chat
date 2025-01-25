package com.chatting.domain.chat.presentation.dto;

import com.chatting.domain.member.domain.Member;
import com.chatting.domain.member.presentation.dto.MemberResponse;
import lombok.Builder;

public class MessageResponse {
    @Builder
    public record MessageRes(
            Long roomId,
            String message,
            MemberResponse.MemberInfo member
    ) {
        public static MessageRes toDto(Long roomId, String message, Member member) {
            return MessageRes.builder()
                    .roomId(roomId)
                    .message(message)
                    .member(MemberResponse.MemberInfo.toDto(member))
                    .build();
        }
    }
}
