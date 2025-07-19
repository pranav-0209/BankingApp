package com.banking_application.bank.app.account.repository;

import com.banking_application.bank.app.account.model.Account;
import com.banking_application.bank.app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUser(User user);

    Account findByAccountNumber(String accountNumber);
}
