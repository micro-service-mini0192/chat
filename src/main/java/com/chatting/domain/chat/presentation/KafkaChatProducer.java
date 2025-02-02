package com.chatting.domain.chat.presentation;

import com.chatting.config.KafkaConfig;
import com.chatting.domain.chat.presentation.dto.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaChatProducer {

    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaConfig kafkaConfig;

    public void sendMessage(MessageResponse.MessageRes messageInfo) {
        try {
            MessageResponse.KafkaMessageRes kafkaMessageRes = MessageResponse.KafkaMessageRes.builder()
                    .data(messageInfo)
                    .sender(kafkaConfig.getSenderUUID())
                    .build();

            String message = mapper.writeValueAsString(kafkaMessageRes);
            CompletableFuture<SendResult<String, String>> feature = kafkaTemplate.send("chat", message);

            feature.thenAccept(result -> {
                log.info("[Kafka Producer] Topic: {}, Offset: {}, Partition: {}",
                        result.getProducerRecord().topic(),
                        result.getRecordMetadata().offset(),
                        result.getRecordMetadata().partition());
            }).exceptionally(ex -> {
                log.error("[Kafka Producer] Send chat fail", ex);
                return null;
            });
        } catch (JsonProcessingException e) {
            log.error("[Kafka Producer] Couldn't encode chat info", e);
        }
    }
}
