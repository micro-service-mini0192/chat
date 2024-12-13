package com.chatting.domain.room.presentation.dto;

import com.chatting.domain.room.presentation.domain.Room;
import lombok.Builder;

public class RoomRequest {
    @Builder
    public record Save(
            String roomName
    ) {
        public static Room toEntity(String id, Save dto) {
            return Room.builder()
                    .roomName(dto.roomName)
                    .build();
        }
    }
}
