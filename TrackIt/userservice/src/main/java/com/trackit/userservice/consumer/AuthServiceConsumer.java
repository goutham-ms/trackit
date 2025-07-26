package com.trackit.userservice.consumer;

import com.trackit.userservice.dto.UserInfoDto;
import com.trackit.userservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceConsumer {

    private UserInfoService userInfoService;

    @Autowired
    public AuthServiceConsumer(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto eventData) {
        try {
            userInfoService.createOrUpdateUser(eventData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
