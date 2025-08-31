package com.banking_application.bank.app.transaction.mapper;

import com.banking_application.bank.app.account.model.Account;
import com.banking_application.bank.app.transaction.dto.TransactionRequestDTO;
import com.banking_application.bank.app.transaction.dto.TransactionResponseDTO;
import com.banking_application.bank.app.transaction.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    public static Transaction toEntity(TransactionRequestDTO requestDTO, Account fromAccount, Account toAccount) {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setFromAccountNumber(fromAccount.getAccountNumber());
        transaction.setAmount(requestDTO.getAmount());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setTransactionType(requestDTO.getTransactionType());

        if (toAccount != null) {
            transaction.setToAccount(toAccount);
            transaction.setToAccountNumber(toAccount.getAccountNumber());
        } else {
            transaction.setToAccount(null);
            transaction.setToAccountNumber(null);
        }
        return transaction;
    }

    public static TransactionResponseDTO toResponseDTO(Transaction transaction) {

//        String toAccount = transaction.getToAccount() != null ? transaction.getToAccount().getAccountNumber():null;

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getFromAccountNumber(),
                transaction.getToAccountNumber(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getTimestamp()
        );
    }

}
