package com.chatting.domain.room.application;

import com.chatting.domain.chat.domain.Chat;
import com.chatting.domain.members.domain.Member;
import com.chatting.domain.members.domain.MemberRepository;
import com.chatting.domain.room.domain.*;
import com.chatting.domain.room.presentation.dto.RoomRequest;
import com.chatting.domain.room.presentation.dto.RoomResponse;
import com.chatting.exception.AuthedException;
import com.chatting.exception.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(RoomRequest.RoomSave dto, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundDataException("Not Found Room."));;
        Room room = Room.builder()
                .roomName(dto.roomName())
                .build();
        Room saveRoom = roomRepository.save(room);

        RoomMember roomMembers = RoomMember.builder()
                .room(saveRoom)
                .role(RoomRole.ADMIN)
                .member(member)
                .build();
        roomMemberRepository.save(roomMembers);
    }

    public List<RoomResponse.RoomFindAll> findAll() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(RoomResponse.RoomFindAll::toDto).toList();
    }

    public RoomResponse.RoomFindById findById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundDataException("Not Found Room."));
        return RoomResponse.RoomFindById.toDto(room);
    }

    public Page<RoomResponse.ChatPage> findChat(Long id, Pageable pageable) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundDataException("Not Found Room."));
        List<Chat> chats = room.getChats();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), chats.size());
        List<Chat> pageChats = chats.subList(start, end);

        List<RoomResponse.ChatPage> chatPageList = pageChats.stream()
                .map(RoomResponse.ChatPage::toDto)
                .toList();

        return new PageImpl<>(chatPageList, pageable, chats.size());
    }

    @Transactional
    public void delete(Long id, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundDataException("Not Found Member."));
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundDataException("Not Found Room."));

        List<RoomMember> roomMembers = room.getRoomMembers();
        for (RoomMember roomMember : roomMembers) {
            if(!roomMember.getRoom().getId().equals(member.getId())) continue;
            if(!roomMember.getRole().equals(RoomRole.ADMIN)) throw new AuthedException("Member is not ADMIN");
            roomRepository.delete(room);
            return;
        }

        throw new AuthedException("Not Found room");
    }
}
