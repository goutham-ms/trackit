package com.trackit.expenseservice.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackit.expenseservice.dto.ExpenseDto;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ExpenseDeserializer implements Deserializer<ExpenseDto> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public ExpenseDto deserialize(String s, byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        ExpenseDto expense = null;
        try {
            expense = objectMapper.readValue(bytes, ExpenseDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expense;
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}

