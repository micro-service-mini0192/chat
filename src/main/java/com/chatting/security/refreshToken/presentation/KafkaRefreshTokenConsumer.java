package com.chatting.security.refreshToken.presentation;

import com.chatting.security.refreshToken.application.RefreshTokenService;
import com.chatting.security.refreshToken.domain.RefreshTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaRefreshTokenConsumer {

    private final ObjectMapper objectMapper;
    private final RefreshTokenService refreshTokenService;

    @KafkaListener(topics = "refreshToken", groupId = "group1")
    public void getRefreshToken(@Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment ack,
                                @Header(KafkaHeaders.OFFSET) long offset,
                                @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                String message) throws JsonProcessingException {
        RefreshTokenRequest refreshTokenRequest = objectMapper.readValue(message, RefreshTokenRequest.class);
        refreshTokenService.refreshTokenSaveManager(refreshTokenRequest);

        ack.acknowledge();
        log.info("Refresh token save successful, Topic: refreshToken, Offset: {}, Partition: {}", offset, partition);
    }
}
