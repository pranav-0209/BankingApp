package com.banking_application.bank.app.account.controller;

import com.banking_application.bank.app.account.dto.AccountRequestDTO;
import com.banking_application.bank.app.account.dto.AccountResponseDTO;
import com.banking_application.bank.app.account.service.AccountService;
import com.banking_application.bank.app.user.repository.UserRepository;
import com.banking_application.bank.app.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;
    private final UserRepository userRepository;

    public AccountController(AccountService accountService, UserService userService, UserRepository userRepository) {
        this.accountService = accountService;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PostMapping// or remove if your SecurityConfig already restricts access
    public ResponseEntity<AccountResponseDTO> createAccount (
            @RequestBody @Valid AccountRequestDTO requestDTO) {
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                try {
                    throw new AccessDeniedException("You must be authenticated to create an account.");
                } catch (AccessDeniedException e) {
                    throw new RuntimeException(e);
                }
            }
            String username = authentication.getName();
            AccountResponseDTO responseDTO = accountService.createAccount(requestDTO, username);
            return ResponseEntity.ok(responseDTO);
    }


}
