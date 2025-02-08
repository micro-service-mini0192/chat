package com.chatting.domain.room.application;

import com.chatting.domain.chat.domain.Chat;
import com.chatting.domain.chat.domain.ChatRepository;
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
import org.springframework.data.domain.PageRequest;
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
    private final ChatRepository chatRepository;

    @Transactional
    public void save(RoomRequest.RoomSave dto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundDataException("Not found member."));;
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

    public List<RoomResponse.RoomFindAll> findAll(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundDataException("Not found member."));
        return member.getRoomMember().stream().map(roomMember -> RoomResponse.RoomFindAll.toDto(roomMember.getRoom())).toList();
    }

    public RoomResponse.RoomFindById findById(Long id, Long memberId) {
        RoomMember roomMember = roomMemberRepository.findRoomMemberOfRoomAndMember(id, memberId)
                .orElseThrow(() -> new NotFoundDataException("Not found room."));
        return RoomResponse.RoomFindById.toDto(roomMember.getRoom());
    }

    public Page<RoomResponse.ChatPage> findChat(Long id, int page, Long memberId) {
        RoomMember roomMember = roomMemberRepository.findRoomMemberOfRoomAndMember(id, memberId)
                .orElseThrow(() -> new NotFoundDataException("Not found room."));
        Pageable pageable = PageRequest.of(page - 1, 50);
        Page<Chat> chatPage = chatRepository.findByRoomIdPage(roomMember.getRoom().getId(), pageable);
        return chatPage.map(RoomResponse.ChatPage::toDto);
    }

    @Transactional
    public void delete(Long id, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundDataException("Not found member."));
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundDataException("Not found room."));

        List<RoomMember> roomMembers = room.getRoomMembers();
        for (RoomMember roomMember : roomMembers) {
            if(!roomMember.getMember().getId().equals(member.getId())) continue;
            if(!roomMember.getRole().equals(RoomRole.ADMIN)) throw new AuthedException("Member is not ADMIN");
            roomRepository.delete(room);
            return;
        }

        throw new AuthedException("Member do not belongs to this room");
    }

    @Transactional
    public void join(Long id, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundDataException("Not found member."));
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundDataException("Not found room."));
        if(roomMemberRepository.isMemberBelongRoom(id, memberId)) throw new AuthedException("Member is already belong to this room");

        RoomMember roomMember = RoomMember.builder()
                .room(room)
                .member(member)
                .role(RoomRole.MEMBER)
                .build();

        roomMemberRepository.save(roomMember);
    }
}
