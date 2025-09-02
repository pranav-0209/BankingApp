package com.banking_application.bank.app.account.service;

import com.banking_application.bank.app.account.dto.AccountRequestDTO;
import com.banking_application.bank.app.account.dto.AccountResponseDTO;
import com.banking_application.bank.app.account.mapper.AccountMapper;
import com.banking_application.bank.app.account.model.Account;
import com.banking_application.bank.app.account.repository.AccountRepository;
import com.banking_application.bank.app.user.model.User;
import com.banking_application.bank.app.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO requestDTO, String authenticatedUsername) {
        User user = userRepository.findByName(authenticatedUsername);

        Account acc = AccountMapper.toEntity(requestDTO, user);

        acc.setCreatedAt(LocalDateTime.now());

        Account account = accountRepository.save(acc);

        String accountNumber;
        if ("SAVINGS".equalsIgnoreCase(requestDTO.getAccountType())) {
            accountNumber = "1" + String.format("%09d", account.getAid());
        } else if ("CURRENT".equalsIgnoreCase(requestDTO.getAccountType())) {
            accountNumber = "2" + String.format("%09d", account.getAid());
        } else {
            // Fallback for any other account types
            accountNumber = "9" + String.format("%09d", account.getAid());
        }

        account.setAccountNumber(accountNumber);

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.toResponseDto(savedAccount);
    }

    public List<AccountResponseDTO> getAccountsForAuthenticatedUser(String username) {

        User user = userRepository.findByName(username);
        List<Account> accounts = accountRepository.findByUser(user);
        return accounts.stream()
                .map(account -> new AccountResponseDTO(
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getBalance(),
                        account.getUser().getName(),
                        account.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public AccountResponseDTO getAccountDetails(String username, String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (!account.getUser().getName().equals(username)) {
            throw new AccessDeniedException("You are not authorized to delete this account.");
        }

        return AccountMapper.toResponseDto(account);
    }

    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(AccountMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public Double getAccountBalance(String accountNumber, String username) {

        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (!account.getUser().getName().equals(username)) {
            throw new AccessDeniedException("You are not authorized to delete this account.");
        }

        return account.getBalance();
    }

    @SneakyThrows
    @Transactional
    public void deleteAccount(String accountNumber, String username) {

        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (!account.getUser().getName().equals(username)) {
            throw new AccessDeniedException("You are not authorized to delete this account.");
        }

        accountRepository.deleteById(account.getAid());
    }

}
