package com.chatting.domain.room.presentation.dto;

import com.chatting.domain.room.domain.Room;
import lombok.Builder;

public class RoomRequest {
    @Builder
    public record RoomSave(
            String roomName
    ) {
        public static Room toEntity(String id, RoomSave dto) {
            return Room.builder()
                    .roomName(dto.roomName)
                    .build();
        }
    }
}
