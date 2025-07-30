package com.trackit.dsservice.controller;

import com.trackit.dsservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private MessageService messageService;
    @Autowired
    MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> user(@RequestBody String message) {
        try {
            messageService.processMessage(message);
            return new ResponseEntity<>("Success! ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
