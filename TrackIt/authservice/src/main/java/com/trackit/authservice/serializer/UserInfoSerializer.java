package com.trackit.authservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackit.authservice.model.UserInfoDto;
import com.trackit.authservice.model.UserInfoEventDto;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoEventDto> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, UserInfoEventDto userInfoDto) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(userInfoDto).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }


    @Override
    public void close() {
        Serializer.super.close();
    }
}
