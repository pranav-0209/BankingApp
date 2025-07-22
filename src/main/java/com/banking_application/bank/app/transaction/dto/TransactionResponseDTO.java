package com.banking_application.bank.app.transaction.dto;

import java.time.LocalDateTime;

public record TransactionResponseDTO(  Long id,
                                       String fromAccount,
                                       // This maps to transaction.fromAccountNumber
                                       String toAccount,   // This maps to transaction.toAccountNumber
                                       String transactionType,
                                       Double amount,
                                       LocalDateTime timestamp
                                    ) {}
