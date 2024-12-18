package com.chatting.domain.room.application;

import com.chatting.domain.room.domain.Room;
import com.chatting.domain.room.domain.RoomRepository;
import com.chatting.domain.room.presentation.dto.RoomRequest;
import com.chatting.domain.room.presentation.dto.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomResponse.RoomFindById save(RoomRequest.RoomSave dto) {
        String id = UUID.randomUUID().toString();
        Room room = RoomRequest.RoomSave.toEntity(id, dto);
        Room saveRoom = roomRepository.save(room);
        return RoomResponse.RoomFindById.toDto(saveRoom);
    }

    public List<RoomResponse.RoomFindAll> findAll() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(RoomResponse.RoomFindAll::toDto).toList();
    }

    public RoomResponse.RoomFindById findById(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        return RoomResponse.RoomFindById.toDto(room);
    }

    public void delete(Long id) {
        roomRepository.deleteById(id);
    }
}
