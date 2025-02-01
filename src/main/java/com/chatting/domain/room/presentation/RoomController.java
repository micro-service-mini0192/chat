package com.chatting.domain.room.presentation;

import com.chatting.domain.room.application.RoomService;
import com.chatting.domain.room.presentation.dto.RoomRequest;
import com.chatting.domain.room.presentation.dto.RoomResponse;
import com.chatting.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create room", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<RoomResponse.RoomFindById> save(@Valid @RequestBody RoomRequest.RoomSave dto) {
        Long memberId = SecurityUtil.getCurrentMember();
        roomService.save(dto, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find rooms", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<RoomResponse.RoomFindAll>> findAll() {
        Long memberId = SecurityUtil.getCurrentMember();
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findAll(memberId));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find a room", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<RoomResponse.RoomFindById> findById(@PathVariable("id") Long id) {
        Long memberId = SecurityUtil.getCurrentMember();
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findById(id, memberId));
    }

    @GetMapping(value = "/chats/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find chats", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Page<RoomResponse.ChatPage>> findByChatId(@PathVariable("id") Long id, @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
        Long memberId = SecurityUtil.getCurrentMember();
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findChat(id, page, memberId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete room", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Long memberId = SecurityUtil.getCurrentMember();
        roomService.delete(id, memberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/join/{id}")
    @Operation(summary = "Join room", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> join(@PathVariable("id") Long id) {
        Long memberId = SecurityUtil.getCurrentMember();
        roomService.join(id, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
