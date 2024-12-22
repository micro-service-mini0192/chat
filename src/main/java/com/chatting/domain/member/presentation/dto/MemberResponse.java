package com.chatting.domain.member.presentation.dto;

import com.chatting.domain.member.domain.Member;
import lombok.Builder;

public class MemberResponse {
    @Builder
    public record MemberInfo(
            Long id,
            String nickname
    ) {
        public static MemberInfo toDto(Member member) {
            return MemberInfo.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .build();
        }
    }
}
