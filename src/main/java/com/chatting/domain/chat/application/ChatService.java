package com.chatting.domain.chat.application;

import com.chatting.domain.chat.domain.ChatRepository;
import com.chatting.domain.chat.presentation.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public void chatting(MessageResponse.MessageRes dto) {

    }
}
