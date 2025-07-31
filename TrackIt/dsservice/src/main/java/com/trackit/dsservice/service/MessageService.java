package com.trackit.dsservice.service;

import com.trackit.dsservice.entity.Expense;
import com.trackit.dsservice.producer.ExpenseEventProducer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class MessageService {

    private final ChatClient chatClient;

    @Autowired
    private  ExpenseEventProducer expenseEventProducer;

    public MessageService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultOptions(ChatOptions.builder().temperature(0.0d).build())
                .build();
    }

    @Value("classpath:/prompts/prompt.st")
    private Resource promptTemplate;

    public Expense llmCall(String message) {
      var system = new SystemMessage(promptTemplate);
      var msg = new UserMessage(message);

      return chatClient.prompt(new Prompt(List.of(system, msg)))
              .call()
              .entity(Expense.class);
    }

    public boolean isBankSms(String message) {
        int score = 0;
        message = message.trim();
        System.out.println(message);
        int flags = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
        if (Pattern.compile("\\b(spent|debited|bank|card|transaction|purchase)\\b", flags)
                .matcher(message).find()) {
            score++;
        }
        if (Pattern.compile("\\b(Rs\\.?|INR|₹)\\s?\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?\\b", flags)
                .matcher(message).find()) {
            score++;
        }
        if (Pattern.compile("\\b(ending\\s*\\d{4}|XXXX\\d{4}|a/c\\s*\\w+)\\b", flags)
                .matcher(message).find()) {
            score++;
        }
        System.out.println(score);
        return score >= 2;
    }

    public void processMessage(String message) {
        if(isBankSms(message)) {
            Expense msg = llmCall(message);
            expenseEventProducer.sendEventToKafka(msg);
            System.out.println(msg);
        }
    }

}
