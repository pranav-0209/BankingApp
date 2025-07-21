package com.banking_application.bank.app.transaction.dto;

import com.banking_application.bank.app.account.model.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotBlank(message = "Account number is required")
    private String fromAccountNumber;

    String toAccountNumber;

    @NotBlank(message = "Transaction type is required")   // e.g., "DEPOSIT", "WITHDRAWAL", "TRANSFER"
    private String transactionType;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be positive")
    private Double amount;



}
