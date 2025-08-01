package com.trackit.expenseservice.consumer;

import com.trackit.expenseservice.dto.ExpenseDto;
import com.trackit.expenseservice.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExpenseConsumer {
    @Autowired
    private ExpenseService expenseService;

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ExpenseDto eventData) {
        try {
            expenseService.createExpense(eventData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

