package com.chatting.domain.room.presentation.dto;

import com.chatting.domain.room.domain.Room;
import com.chatting.domain.room.domain.RoomMember;
import com.chatting.domain.room.domain.RoomRole;
import lombok.Builder;

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
}
