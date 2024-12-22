package com.chatting.domain.chat.presentation;

import com.chatting.domain.chat.presentation.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(MessageResponse.MessageRes messageRes) {
        template.convertAndSend("/topic/"+ messageRes.id(), messageRes);
    }
}