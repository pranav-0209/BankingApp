package com.banking_application.bank.app.transaction.dto;

import java.time.LocalDateTime;

public record TransactionResponseDTO(Long id,
                                     String accountNumber,
                                     String toAccountNumber,
                                     String transactionType,
                                     Double amount,
                                     LocalDateTime timestamp
                                    ) {}
