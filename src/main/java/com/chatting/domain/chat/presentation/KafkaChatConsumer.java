package com.chatting.domain.chat.presentation;

import com.chatting.config.KafkaConfig;
import com.chatting.domain.chat.presentation.dto.MessageResponse;
import com.chatting.security.refreshToken.application.RefreshTokenService;
import com.chatting.security.refreshToken.presentation.RefreshTokenRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaChatConsumer {

    private final SimpMessagingTemplate template;
    private final ObjectMapper objectMapper;
    private final KafkaConfig kafkaConfig;

    @KafkaListener(topics = "chat", groupId = "group2")
    public void getRefreshToken(@Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment ack,
                                @Header(KafkaHeaders.OFFSET) long offset,
                                @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                String message) throws JsonProcessingException {
        MessageResponse.KafkaMessageRes mapper = objectMapper.readValue(message, MessageResponse.KafkaMessageRes.class);
        if(mapper.sender().equals(kafkaConfig.getServerPort())) {
            log.info("Chat pass.");
            ack.acknowledge();
            return;
        }

        MessageResponse.MessageRes dto = mapper.data();
        template.convertAndSend("/topic/"+ dto.roomId(), dto);
        ack.acknowledge();
        log.info("Chat send successful, Topic: chat, Offset: {}, Partition: {}", offset, partition);
    }
}
