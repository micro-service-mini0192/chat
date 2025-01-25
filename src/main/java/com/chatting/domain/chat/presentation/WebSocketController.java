package com.chatting.domain.chat.presentation;

import com.chatting.domain.chat.application.ChatService;
import com.chatting.domain.chat.presentation.dto.MessageRequest;
import com.chatting.domain.chat.presentation.dto.MessageResponse;
import com.chatting.domain.member.domain.Member;
import com.chatting.domain.member.domain.MemberDetails;
import com.chatting.domain.member.presentation.dto.MemberResponse;
import com.chatting.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;
    private final JwtProvider jwtProvider;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message<?> message, MessageRequest.MessageReq dto) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String headerToken = accessor.getFirstNativeHeader("Authorization");
        MemberDetails memberDetails = jwtProvider.getMemberDetails(headerToken);

        Member member = Member.builder()
                .id(memberDetails.getMemberId())
                .nickname(memberDetails.getNickname())
                .build();

        System.out.println("test");

        MessageResponse.MessageRes messageRes = MessageResponse.MessageRes.toDto(dto.roomId(), "test", member);
        template.convertAndSend("/topic/"+ dto.roomId(), messageRes);
    }
}