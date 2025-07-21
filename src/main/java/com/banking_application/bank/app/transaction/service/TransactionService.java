package com.banking_application.bank.app.transaction.service;

import com.banking_application.bank.app.account.dto.AccountResponseDTO;
import com.banking_application.bank.app.account.mapper.AccountMapper;
import com.banking_application.bank.app.account.model.Account;
import com.banking_application.bank.app.account.repository.AccountRepository;
import com.banking_application.bank.app.account.service.AccountService;
import com.banking_application.bank.app.transaction.dto.TransactionResponseDTO;
import com.banking_application.bank.app.transaction.repository.TransactionRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @SneakyThrows
    public AccountResponseDTO deposit(String username, String accountNumber, Double amount){
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (!account.getUser().getName().equals(username)) {
            throw new AccessDeniedException("You are not authorized to deposit into this account.");
        }
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);

            return AccountMapper.toResponseDto(account);


    }

}
