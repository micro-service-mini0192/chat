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
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketManager {

    private final SimpMessagingTemplate template;
    private final KafkaChatProducer kafkaChatProducer;
    private final ChatService chatService;
    private final JwtProvider jwtProvider;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message<?> message, @Payload MessageRequest.MessageReq dto) {
        if(!dto.vaild()) return;

        // 헤더 정보를 받아옴
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String headerToken = accessor.getFirstNativeHeader("Authorization");

        // getMemberDetails -> member로 변환
        MemberDetails memberDetails = jwtProvider.getMemberDetails(headerToken);
        Member member = memberDetails.getMember();

        // 데이터 DB에 저장 및 kafka에 전송
        MessageResponse.MessageRes messageRes = MessageResponse.MessageRes.toDto(dto.roomId(), dto.message(), member);
        kafkaChatProducer.sendMessage(messageRes);
        chatService.save(dto, member);

        // /topic/{id}에 데이터 전송
        template.convertAndSend("/topic/"+ dto.roomId(), messageRes);
    }
}