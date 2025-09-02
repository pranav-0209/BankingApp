package com.banking_application.bank.app.transaction.model;

import com.banking_application.bank.app.account.model.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String transactionType;
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account fromAccount;

    @Column(name = "from_account_number_str", nullable = false)
    private String fromAccountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account toAccount;

    @Column(name = "to_account_number_str", nullable = true) // Add this field
    private String toAccountNumber;

}
