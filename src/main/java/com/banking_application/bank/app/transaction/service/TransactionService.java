package com.banking_application.bank.app.transaction.service;

import com.banking_application.bank.app.account.model.Account;
import com.banking_application.bank.app.account.repository.AccountRepository;
import com.banking_application.bank.app.account.service.AccountService;
import com.banking_application.bank.app.transaction.dto.TransactionRequestDTO;
import com.banking_application.bank.app.transaction.dto.TransactionResponseDTO;
import com.banking_application.bank.app.transaction.mapper.TransactionMapper;
import com.banking_application.bank.app.transaction.model.Transaction;
import com.banking_application.bank.app.transaction.repository.TransactionRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

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
    public TransactionResponseDTO deposit(String username, TransactionRequestDTO dto) {

        Account account = accountRepository.findByAccountNumber(dto.getAccount());
        if (!account.getUser().getName().equals(username)) {
            throw new AccessDeniedException("You are not authorized to deposit into this account.");
        }
        account.setBalance(account.getBalance() + dto.getAmount());
        accountRepository.save(account);

        Transaction transaction = TransactionMapper.toEntity(dto, account, null); // Pass null for toAccount
        transaction.setTransactionType("DEPOSIT"); // Ensure type is explicitly set here or in DTO

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.toResponseDTO(savedTransaction);

    }


    @SneakyThrows
    public TransactionResponseDTO withdraw(String username, TransactionRequestDTO dto) {

        Account account = accountRepository.findByAccountNumber(dto.getAccount());
        if (!account.getUser().getName().equals(username)) {
            throw new AccessDeniedException("You are not authorized to deposit into this account.");
        }
        account.setBalance(account.getBalance() - dto.getAmount());
        accountRepository.save(account);

        Transaction transaction = TransactionMapper.toEntity(dto, account, null); // Pass null for toAccount
        transaction.setTransactionType("WITHDRAW"); // Ensure type is explicitly set here or in DTO

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.toResponseDTO(savedTransaction);

    }

    @SneakyThrows
    public TransactionResponseDTO transfer(String username, TransactionRequestDTO requestDTO) {
        // Source account (fromAccountNumber in DTO)
        Account fromAccount = accountRepository.findByAccountNumber(requestDTO.getAccount());

        // Destination account (toAccountNumber in DTO)
        Account toAccount = accountRepository.findByAccountNumber(requestDTO.getToAccount());

        if (fromAccount.getBalance() < requestDTO.getAmount()) {
            throw new RuntimeException("Insufficient funds in source account: " + requestDTO.getAccount());
        }

        // Perform transfer logic
        fromAccount.setBalance(fromAccount.getBalance() - requestDTO.getAmount());
        toAccount.setBalance(toAccount.getBalance() + requestDTO.getAmount());

        accountRepository.save(fromAccount); // Save updated source account
        accountRepository.save(toAccount);   // Save updated destination account

        // Create transaction record
        Transaction transaction = TransactionMapper.toEntity(requestDTO, fromAccount, toAccount);
        transaction.setTransactionType("TRANSFER"); // Ensure type is explicitly set

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.toResponseDTO(savedTransaction);
    }

}
