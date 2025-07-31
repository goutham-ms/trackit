package com.trackit.expenseservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String externalId;
    private String userId;
    private String amount;
    private String currency;
    private String merchant;
    private Timestamp createdAt;

    @PrePersist
    @PreUpdate
    private void generateExternalId() {
        if(this.externalId == null) {
            this.externalId = UUID.randomUUID().toString();
        }
    }
}
