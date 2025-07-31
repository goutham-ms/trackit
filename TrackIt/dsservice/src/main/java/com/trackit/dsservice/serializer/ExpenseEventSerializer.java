package com.trackit.dsservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackit.dsservice.entity.Expense;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ExpenseEventSerializer implements Serializer<Expense> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Expense data) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(data).getBytes();
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

