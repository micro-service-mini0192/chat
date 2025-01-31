package com.chatting.domain.chat.application;

import com.chatting.domain.chat.domain.Chat;
import com.chatting.domain.chat.domain.ChatRepository;
import com.chatting.domain.chat.presentation.dto.MessageRequest;
import com.chatting.domain.chat.presentation.dto.MessageResponse;
import com.chatting.domain.members.domain.Member;
import com.chatting.domain.members.domain.MemberRepository;
import com.chatting.domain.room.domain.Room;
import com.chatting.domain.room.domain.RoomRepository;
import com.chatting.exception.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;

    @Async
    @Transactional
    public void save(MessageRequest.MessageReq message, Member memberData) {
        Member member = memberRepository.findById(memberData.getId()).orElseThrow(() -> new NotFoundDataException("Not Found Member."));
        Room room = roomRepository.findById(message.roomId()).orElseThrow(() -> new NotFoundDataException("Not Found Room."));
        Chat chat = Chat.builder()
                .room(room)
                .member(member)
                .message(message.message())
                .build();
        chatRepository.save(chat);
    }
}
