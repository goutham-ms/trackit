package com.trackit.authservice.event;

import com.trackit.authservice.model.UserInfoDto;
import com.trackit.authservice.model.UserInfoEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProducer {
    private final KafkaTemplate<String, UserInfoEventDto> kafkaTemplate;

    @Value("${spring.kafka.topic-json.name}")
    private String topicJsonName;

    @Autowired
    public UserInfoProducer(KafkaTemplate<String, UserInfoEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEventToKafka(UserInfoEventDto eventData) {
        Message<UserInfoEventDto> message = MessageBuilder.withPayload(eventData)
                .setHeader(KafkaHeaders.TOPIC, topicJsonName).build();
        kafkaTemplate.send(message);
    }
}
