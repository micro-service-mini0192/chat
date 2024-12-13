package com.chatting.domain.room.presentation.application;

import com.chatting.domain.room.presentation.domain.Room;
import com.chatting.domain.room.presentation.domain.RoomRepository;
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

    public RoomResponse.FindById save(RoomRequest.Save dto) {
        String id = UUID.randomUUID().toString();
        Room room = RoomRequest.Save.toEntity(id, dto);
        Room saveRoom = roomRepository.save(room);
        return RoomResponse.FindById.toDto(saveRoom);
    }

    public List<RoomResponse.FindAll> findAll() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(RoomResponse.FindAll::toDto).toList();
    }

    public RoomResponse.FindById findById(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        return RoomResponse.FindById.toDto(room);
    }

    public void delete(Long id) {
        roomRepository.deleteById(id);
    }
}
