package com.trackit.expenseservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackit.expenseservice.dto.ExpenseDto;
import com.trackit.expenseservice.entity.Expense;
import com.trackit.expenseservice.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpenseService {
    private ExpenseRepository expenseRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public boolean createExpense(ExpenseDto expenseDto) {
        setCurrency(expenseDto);
       try {
           Expense expense = objectMapper.convertValue(expenseDto, Expense.class);
           expenseRepository.save(expense);
           return true;
       } catch (Exception e) {
           return false;
       }
    }

    public boolean updateExpense(ExpenseDto expenseDto) {
        Optional<Expense> expenseFound = expenseRepository.findByUseIdAndExternalId(expenseDto.getUserId(), expenseDto.getExternalId());
        if(expenseFound.isEmpty()) {
            return false;
        }
        Expense expense = expenseFound.get();
        expense.setCurrency(Strings.isNotBlank(expenseDto.getCurrency()) ? expenseDto.getCurrency() : expense.getCurrency());
        expense.setMerchant(Strings.isNotBlank(expenseDto.getMerchant()) ? expenseDto.getMerchant() : expense.getMerchant());
        expense.setAmount(expenseDto.getAmount());
        expenseRepository.save(expense);
        return true;
    }


    public boolean deleteExpense(ExpenseDto expenseDto) {
        Optional<Expense> expenseFound = expenseRepository.findByUseIdAndExternalId(expenseDto.getUserId(), expenseDto.getExternalId());
        if(expenseFound.isEmpty()) {
            return false;
        }
        expenseRepository.delete(expenseFound.get());
        return true;
    }

    public List<ExpenseDto> getExpenses(String userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        return objectMapper.convertValue(expenses, new TypeReference<List<ExpenseDto>>(){});
    }

    public List<ExpenseDto> getExpenseBetween(String userId, Timestamp start, Timestamp end) {
        List<Expense> expenses = expenseRepository.findByUserIdAndCreatedAtBetween(userId, start, end);
        return objectMapper.convertValue(expenses, new TypeReference<List<ExpenseDto>>(){});
    }

     private void setCurrency(ExpenseDto expenseDto) {
        if(Objects.isNull(expenseDto.getCurrency())) {
            expenseDto.setCurrency("INR");
        }
    }
}
