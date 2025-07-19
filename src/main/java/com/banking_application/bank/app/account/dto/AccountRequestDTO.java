package com.banking_application.bank.app.account.dto;


import com.banking_application.bank.app.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountRequestDTO {

    @NotBlank(message = "Account type is required")
    private String accountType;

    @NotNull(message = "Initial balance is required")
    private double balance;
}
