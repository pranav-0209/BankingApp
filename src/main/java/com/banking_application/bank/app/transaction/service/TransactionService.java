package com.banking_application.bank.app.transaction.service;

import com.banking_application.bank.app.account.model.Account;
import com.banking_application.bank.app.account.repository.AccountRepository;
import com.banking_application.bank.app.account.service.AccountService;
import com.banking_application.bank.app.transaction.dto.TransactionRequestDTO;
import com.banking_application.bank.app.transaction.dto.TransactionResponseDTO;
import com.banking_application.bank.app.transaction.mapper.TransactionMapper;
import com.banking_application.bank.app.transaction.model.Transaction;
import com.banking_application.bank.app.transaction.repository.TransactionRepository;
import com.banking_application.bank.app.user.model.User;
import com.banking_application.bank.app.user.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountService accountService, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.userRepository = userRepository;
    }


    @SneakyThrows
    public TransactionResponseDTO deposit(String username, TransactionRequestDTO dto) {

        Account account = accountRepository.findByAccountNumber(dto.getAccount());
        if (!account.getUser().getName().equals(username)) {
            throw new AccessDeniedException("You are not authorized to deposit into this account.");
        }
        account.setBalance(account.getBalance() + dto.getAmount());
        accountRepository.save(account);

        Transaction transaction = TransactionMapper.toEntity(dto, account, null); // Pass null for toAccount
        transaction.setTransactionType("DEPOSIT"); // Ensure type is explicitly set here or in DTO

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.toResponseDTO(savedTransaction);

    }


    @SneakyThrows
    public TransactionResponseDTO withdraw(String username, TransactionRequestDTO dto) {

        Account account = accountRepository.findByAccountNumber(dto.getAccount());
        if (!account.getUser().getName().equals(username)) {
            throw new AccessDeniedException("You are not authorized to deposit into this account.");
        }
        account.setBalance(account.getBalance() - dto.getAmount());
        accountRepository.save(account);

        Transaction transaction = TransactionMapper.toEntity(dto, account, null); // Pass null for toAccount
        transaction.setTransactionType("WITHDRAW"); // Ensure type is explicitly set here or in DTO

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.toResponseDTO(savedTransaction);

    }

    @SneakyThrows
    public TransactionResponseDTO transfer(String username, TransactionRequestDTO requestDTO) {
        // Source account (fromAccountNumber in DTO)
        Account fromAccount = accountRepository.findByAccountNumber(requestDTO.getAccount());

        // Destination account (toAccountNumber in DTO)
        Account toAccount = accountRepository.findByAccountNumber(requestDTO.getToAccount());

        if (fromAccount.getBalance() < requestDTO.getAmount()) {
            throw new RuntimeException("Insufficient funds in source account: " + requestDTO.getAccount());
        }

        // Perform transfer logic
        fromAccount.setBalance(fromAccount.getBalance() - requestDTO.getAmount());
        toAccount.setBalance(toAccount.getBalance() + requestDTO.getAmount());

        accountRepository.save(fromAccount); // Save updated source account
        accountRepository.save(toAccount);   // Save updated destination account

        // Create transaction record
        Transaction transaction = TransactionMapper.toEntity(requestDTO, fromAccount, toAccount);
        transaction.setTransactionType("TRANSFER"); // Ensure type is explicitly set

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.toResponseDTO(savedTransaction);
    }


    public List<TransactionResponseDTO> getAllTransactionsForCurrentUser() {
        // 1. Get the username of the currently logged-in user from the security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // 2. Find the user entity and get their list of associated bank accounts
        // (You will need to inject your UserRepository for this)
        User currentUser = userRepository.findByName(username);
//                .orElseThrow(() -> new IllegalStateException("Current user not found in database"));
        List<Account> userAccounts = accountRepository.findByUser(currentUser);

        // 3. Use the repository to fetch all transactions linked to these accounts
        List<Transaction> transactions = transactionRepository
                .findByFromAccountInOrToAccountInOrderByTimestampDesc(userAccounts, userAccounts);

        // 4. Use your existing mapper to convert the list of entities to a list of DTOs
        return transactions.stream()
                .map(TransactionMapper::toResponseDTO) // Using your static mapper method
                .collect(Collectors.toList());
    }

}
