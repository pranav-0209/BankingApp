package com.banking_application.bank.app.account.service;

import com.banking_application.bank.app.account.dto.AccountRequestDTO;
import com.banking_application.bank.app.account.dto.AccountResponseDTO;
import com.banking_application.bank.app.account.mapper.AccountMapper;
import com.banking_application.bank.app.account.model.Account;
import com.banking_application.bank.app.account.repository.AccountRepository;
import com.banking_application.bank.app.user.model.User;
import com.banking_application.bank.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    public AccountResponseDTO createAccount(AccountRequestDTO requestDTO,String authenticatedUsername){
        User user = userRepository.findByName(authenticatedUsername);

        Account acc = AccountMapper.toEntity(requestDTO, user);

        acc.setCreatedAt(LocalDateTime.now());

        Account account = accountRepository.save(acc);

        String accountNumber = String.valueOf(100_000_000L + account.getAid());
        account.setAccountNumber(accountNumber);

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.toResponseDto(savedAccount);
    }


    public List<AccountResponseDTO> getAccountsForAuthenticatedUser(String username){

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






}
