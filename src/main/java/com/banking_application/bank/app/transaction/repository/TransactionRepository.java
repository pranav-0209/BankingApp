package com.banking_application.bank.app.transaction.repository;

import com.banking_application.bank.app.account.model.Account;
import com.banking_application.bank.app.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findByFromAccountNumber(String fromAccountNumber);
    List<Transaction> findByFromAccountInOrToAccountInOrderByTimestampDesc(List<Account> fromAccounts, List<Account> toAccounts);

}

