package com.banking_application.bank.app.account.dto;

import java.time.LocalDateTime;

public record AccountResponseDTO (String accountNumber,
                                  String accountType,
                                  Double balance,
                                  String userName,
                                  LocalDateTime createdAt){}
