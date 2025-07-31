package com.trackit.expenseservice.controller;

import com.trackit.expenseservice.dto.ExpenseDto;
import com.trackit.expenseservice.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class ExpenseController {
    private ExpenseService expenseService;
    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/expense/v1/create")
    public ResponseEntity<Boolean> createExpense(@RequestBody ExpenseDto expenseDto) {
        try {
            Boolean res = expenseService.createExpense(expenseDto);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/expense/v1/update")
    public ResponseEntity<Boolean> updateExpense(@RequestBody ExpenseDto expenseDto) {
        try {
            Boolean res = expenseService.updateExpense(expenseDto);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/expense/v1/delete")
    public ResponseEntity<Boolean> deleteExpense(@RequestBody ExpenseDto expenseDto) {
        try {
            Boolean res = expenseService.deleteExpense(expenseDto);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/expense/v1/{userId}")
    public ResponseEntity<List<ExpenseDto>> getExpense(@PathVariable String userId) {
        try {
            List<ExpenseDto> expenseDto = expenseService.getExpenses(userId);
            return new ResponseEntity<>(expenseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/expense/v1/expenses/{userId}")
    public ResponseEntity<List<ExpenseDto>> getExpenseBetween(
            @PathVariable String userId,
            @RequestParam Timestamp start,
            @RequestParam Timestamp end) {
        try {
            List<ExpenseDto> expenseDto = expenseService.getExpenseBetween(userId, start, end);
            return new ResponseEntity<>(expenseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
