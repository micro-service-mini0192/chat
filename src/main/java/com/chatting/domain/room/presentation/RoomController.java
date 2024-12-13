package com.chatting.domain.room.presentation;

import com.chatting.domain.room.presentation.application.RoomService;
import com.chatting.domain.room.presentation.dto.RoomRequest;
import com.chatting.domain.room.presentation.dto.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse.FindById> save(@RequestBody RoomRequest.Save dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse.FindAll>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse.FindById> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
