package com.trackit.expenseservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDto {
    private String externalId;
    @JsonProperty(value = "user_id")
    private String userId;
    @NonNull
    @JsonProperty(value = "amount")
    private String amount;
    @JsonProperty(value = "merchant")
    private String merchant;
    @JsonProperty(value = "currency")
    private String currency;
    @JsonProperty(value = "created_at")
    private Timestamp createdAt;

}
