package com.yaelev.bank.service;

import com.yaelev.bank.model.Customer;
import com.yaelev.bank.model.TransactionAccount;
import com.yaelev.bank.repository.TransactionAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionAccountService {

    private final TransactionAccountRepository transactionAccountRepository;

    @Autowired
    public TransactionAccountService(TransactionAccountRepository transactionAccountRepository) {
        this.transactionAccountRepository = transactionAccountRepository;
    }

    public List<TransactionAccount> getTransactionAccounts() {
        return (List<TransactionAccount>) transactionAccountRepository.findAll();
    }

    public ResponseEntity<TransactionAccount> getTransactionAccountById(long id) {
        TransactionAccount transactionAccount = transactionAccountRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Transaction account with id " + id + " doesn't exist")
        );
        return ResponseEntity.ok(transactionAccount);
    }

    public void registerTransactionAccount(TransactionAccount transactionAccount) {
        Optional<TransactionAccount> foundByAccountNo = transactionAccountRepository
                .findTrAccountByAccountNo(transactionAccount.getAccountNo());

        if (foundByAccountNo.isPresent()) {
            throw new IllegalStateException(transactionAccount.getAccountNo() + " is used by another user");
        } else {
            System.out.println("New account registered: " + transactionAccount);
            transactionAccountRepository.save(transactionAccount);
        }



    }

    public void updateTransactionAccount(long id, TransactionAccount transactionAccount) {

        // Find and retrieve if the transactionAccount already exist
        TransactionAccount existingTransactionAccount = transactionAccountRepository.findById(id).
                orElseThrow( () -> new IllegalStateException("No transaction account with id " + id));

        // Check if account no is already in use
        Optional<TransactionAccount> foundByAccountNo = transactionAccountRepository
                .findTrAccountByAccountNo(transactionAccount.getAccountNo());
        if (foundByAccountNo.isPresent()) {
            throw new IllegalStateException(transactionAccount.getAccountNo() + " is used by another user");
        } else if (transactionAccount.getAccountNo().isEmpty()) {
            throw new IllegalStateException("Account number field is empty");
        } else {
            existingTransactionAccount.setAccountNo(transactionAccount.getAccountNo());
        }

        // set initial balance; setBalance in TransactionAccountClass already has validation
        existingTransactionAccount.setBalance(transactionAccount.getBalance());

        // Set customer/owner
        existingTransactionAccount.setCustomer(transactionAccount.getCustomer());

    }
}
