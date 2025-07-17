package com.banking_application.bank.app.user.dto;

import com.banking_application.bank.app.user.model.Role;
import java.time.LocalDateTime;
import java.util.Set;


public record UserResponseDTO(
        long id,
        String name,
        String email,
        String phoneNumber,
        Set<Role> roles,
        LocalDateTime createdAt
        ) {}
