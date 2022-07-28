package com.yaelev.bank.service;

import com.yaelev.bank.model.Customer;
import com.yaelev.bank.model.TransactionAccount;
import com.yaelev.bank.repository.TransactionAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
