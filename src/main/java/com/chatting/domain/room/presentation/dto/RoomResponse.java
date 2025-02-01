package com.chatting.domain.room.presentation.dto;

import com.chatting.domain.chat.domain.Chat;
import com.chatting.domain.members.domain.Member;
import com.chatting.domain.room.domain.Room;
import com.chatting.domain.room.domain.RoomMember;
import com.chatting.domain.room.domain.RoomRole;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomResponse {

    @Builder
    public record RoomFindById(
            Long id,
            String roomName,
            List<MemberInfo> members
    ) {
        @Builder
        private record MemberInfo(
                Long id,
                RoomRole role,
                String nickname
        ) {}
        public static RoomFindById toDto(Room entity) {
            List<MemberInfo> membersInfo = entity.getRoomMembers().stream().map(member -> MemberInfo.builder()
                    .id(member.getMember().getId())
                    .role(member.getRole())
                    .nickname(member.getMember().getNickname())
                    .build()).toList();

            return RoomFindById.builder()
                    .id(entity.getId())
                    .roomName(entity.getRoomName())
                    .members(membersInfo)
                    .build();
        }
    }

    @Builder
    public record RoomFindAll(
            Long id,
            String roomName
    ) {
        public static RoomFindAll toDto(Room entity) {
            return RoomFindAll.builder()
                    .id(entity.getId())
                    .roomName(entity.getRoomName())
                    .build();
        }
    }

    @Builder
    public record ChatPage(
            MemberInfo member,
            String message,
            LocalDateTime createAt
    ) {
        @Builder
        public record MemberInfo(
                Long memberId,
                String nickname
        ) {}
        public static ChatPage toDto(Chat entity) {
            MemberInfo memberInfo = MemberInfo.builder()
                    .memberId(entity.getMember().getId())
                    .nickname(entity.getMember().getNickname())
                    .build();

            return ChatPage.builder()
                    .member(memberInfo)
                    .message(entity.getMessage())
                    .createAt(entity.getCreateAt())
                    .build();
        }
    }
}
