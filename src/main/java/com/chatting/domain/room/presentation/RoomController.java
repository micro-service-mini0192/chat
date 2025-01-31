package com.chatting.domain.room.presentation;

import com.chatting.domain.room.application.RoomService;
import com.chatting.domain.room.presentation.dto.RoomRequest;
import com.chatting.domain.room.presentation.dto.RoomResponse;
import com.chatting.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse.RoomFindById> save(@RequestBody RoomRequest.RoomSave dto) {
        Long memberId = SecurityUtil.getCurrentMember();
        roomService.save(dto, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse.RoomFindAll>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse.RoomFindById> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findById(id));
    }

    @GetMapping("/chats/{id}")
    public ResponseEntity<Page<RoomResponse.ChatPage>> findByChatId(@PathVariable("id") Long id, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findChat(id, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Long memberId = SecurityUtil.getCurrentMember();
        roomService.delete(id, memberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/Join/{id}")
    public ResponseEntity<Void> join(@PathVariable("id") Long id) {
        return null;
    }
}
