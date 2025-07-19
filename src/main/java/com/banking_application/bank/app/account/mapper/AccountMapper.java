package com.banking_application.bank.app.account.mapper;

import com.banking_application.bank.app.account.dto.AccountRequestDTO;
import com.banking_application.bank.app.account.dto.AccountResponseDTO;
import com.banking_application.bank.app.account.model.Account;
import com.banking_application.bank.app.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public static Account toEntity(AccountRequestDTO dto, User user){
        Account account = new Account();
        account.setAccountType(dto.getAccountType());
        account.setBalance(dto.getBalance());
        account.setUser(user);

        return account;

    }

    public static AccountResponseDTO toResponseDto(Account account){
        return new AccountResponseDTO(
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getUser().getName(),
                account.getCreatedAt()
        );
    }

}
