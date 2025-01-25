package com.chatting.domain.room.presentation;

import com.chatting.domain.member.domain.MemberDetails;
import com.chatting.domain.room.application.RoomService;
import com.chatting.domain.room.presentation.dto.RoomRequest;
import com.chatting.domain.room.presentation.dto.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse.RoomFindById> save(@RequestBody RoomRequest.RoomSave dto,
                                                          @AuthenticationPrincipal MemberDetails memberDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse.RoomFindAll>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse.RoomFindById> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/Join/{id}")
    public ResponseEntity<Void> join(@PathVariable("id") Long id) {
        return null;
    }
}
