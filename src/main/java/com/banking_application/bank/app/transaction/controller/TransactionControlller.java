package com.banking_application.bank.app.transaction.controller;

import com.banking_application.bank.app.transaction.dto.TransactionRequestDTO;
import com.banking_application.bank.app.transaction.dto.TransactionResponseDTO;
import com.banking_application.bank.app.transaction.model.Transaction;
import com.banking_application.bank.app.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionControlller {

    private final TransactionService transactionService;

    public TransactionControlller(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/deposit")
    @PreAuthorize("isAuthenticated()") // Only authenticated users can perform deposits
    public ResponseEntity<TransactionResponseDTO> deposit(
            Authentication authentication,
            @Valid @RequestBody TransactionRequestDTO requestDTO) {

        String username = authentication.getName();

        TransactionResponseDTO responseDTO = transactionService.deposit(username, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("isAuthenticated()") // Only authenticated users can perform deposits
    public ResponseEntity<TransactionResponseDTO> withdraw(
            Authentication authentication,
            @Valid @RequestBody TransactionRequestDTO requestDTO) {

        String username = authentication.getName();

        TransactionResponseDTO responseDTO = transactionService.withdraw(username, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    @PreAuthorize("isAuthenticated()") // Only authenticated users can perform deposits
    public ResponseEntity<TransactionResponseDTO> transfer(
            Authentication authentication,
            @Valid @RequestBody TransactionRequestDTO requestDTO) {

        String username = authentication.getName();

        TransactionResponseDTO responseDTO = transactionService.transfer(username, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        List<TransactionResponseDTO> transactions = transactionService.getAllTransactionsForCurrentUser();
        return ResponseEntity.ok(transactions);
    }

}
