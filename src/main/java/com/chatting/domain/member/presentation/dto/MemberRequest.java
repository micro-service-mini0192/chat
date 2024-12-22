package com.chatting.domain.member.presentation.dto;

import com.chatting.domain.member.domain.Member;
import lombok.Builder;

public class MemberRequest {
    @Builder
    public record MemberSave(
            String username,
            String password,
            String nickname
    ) {
        public static Member toEntity(MemberSave dto, String password) {
            if(password.equals(dto.password)) return null;
            return Member.builder()
                    .role(MemberRole.USER.getRole())
                    .username(dto.username)
                    .nickname(dto.nickname)
                    .password(password)
                    .build();
        }
    }
}
