package com.banking_application.bank.app.transaction.mapper;

import com.banking_application.bank.app.account.model.Account;
import com.banking_application.bank.app.transaction.dto.TransactionRequestDTO;
import com.banking_application.bank.app.transaction.dto.TransactionResponseDTO;
import com.banking_application.bank.app.transaction.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public static Transaction toEntity(TransactionRequestDTO requestDTO, Account account) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType(requestDTO.getTransactionType());
        transaction.setAmount(requestDTO.getAmount());
        return transaction;
    }

    public static TransactionResponseDTO toResponseDTO(Transaction transaction) {

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAccount().getAccountNumber(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getTimestamp()
        );
    }

}
