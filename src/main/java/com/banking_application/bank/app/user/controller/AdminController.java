package com.banking_application.bank.app.user.controller;

import com.banking_application.bank.app.account.dto.AccountResponseDTO;
import com.banking_application.bank.app.account.service.AccountService;
import com.banking_application.bank.app.user.dto.UserRequestDTO;
import com.banking_application.bank.app.user.dto.UserResponseDTO;
import com.banking_application.bank.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final AccountService accountService;
    private final UserDetailsService userDetailsService;

    public AdminController(UserService userService, AccountService accountService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.accountService = accountService;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping("/create-admin-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createAdmin(@RequestBody UserRequestDTO userRequestDTO) {
        userService.saveAdmin(userRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUser() {
        List<UserResponseDTO> users = userService.getAllUser();

        if (users == null || users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/all-accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        List<AccountResponseDTO> accountResponseDTOS = accountService.getAllAccounts();

        if (accountResponseDTOS == null || accountResponseDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountResponseDTOS, HttpStatus.OK);
    }




}
