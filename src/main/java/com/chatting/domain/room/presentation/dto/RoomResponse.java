package com.chatting.domain.room.presentation.dto;

import com.chatting.domain.room.presentation.domain.Room;
import lombok.Builder;

public class RoomResponse {

    @Builder
    public record FindById (
            Long id,
            String roomName
    ) {
        public static FindById toDto(Room entity) {
            return FindById.builder()
                    .id(entity.getId())
                    .roomName(entity.getRoomName())
                    .build();
        }
    }

    @Builder
    public record FindAll (
            Long id,
            String roomName
    ) {
        public static FindAll toDto(Room entity) {
            return FindAll.builder()
                    .id(entity.getId())
                    .roomName(entity.getRoomName())
                    .build();
        }
    }
}
