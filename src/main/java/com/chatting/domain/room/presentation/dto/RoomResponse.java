package com.chatting.domain.room.presentation.dto;

import com.chatting.domain.room.domain.Room;
import lombok.Builder;

public class RoomResponse {

    @Builder
    public record RoomFindById(
            Long id,
            String roomName
    ) {
        public static RoomFindById toDto(Room entity) {
            return RoomFindById.builder()
                    .id(entity.getId())
                    .roomName(entity.getRoomName())
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
