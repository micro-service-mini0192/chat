package com.chatting.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Component
public class KafkaConfig {
    @Value("${kafka.url}")
    private String kafkaUrl;

    private final String senderUUID = UUID.randomUUID().toString();
}
