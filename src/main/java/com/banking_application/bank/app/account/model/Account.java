package com.banking_application.bank.app.account.model;

import com.banking_application.bank.app.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Aid;

    private String accountNumber;
    private double balance;
    private String accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Uid")
    private User user;

    private LocalDateTime createdAt;

}
