package com.chatting.domain.chat.presentation;

import com.chatting.domain.chat.application.ChatService;
import com.chatting.domain.chat.presentation.dto.MessageRequest;
import com.chatting.domain.chat.presentation.dto.MessageResponse;
import com.chatting.domain.members.domain.Member;
import com.chatting.domain.members.domain.MemberDetails;
import com.chatting.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketManager {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;
    private final JwtProvider jwtProvider;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message<?> message, MessageRequest.MessageReq dto) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String headerToken = accessor.getFirstNativeHeader("Authorization");
        MemberDetails memberDetails = jwtProvider.getMemberDetails(headerToken);
        Member member = memberDetails.getMember();

        MessageResponse.MessageRes messageRes = MessageResponse.MessageRes.toDto(dto.roomId(), "test", member);
        template.convertAndSend("/topic/"+ dto.roomId(), messageRes);
        chatService.save(dto, member);
    }
}