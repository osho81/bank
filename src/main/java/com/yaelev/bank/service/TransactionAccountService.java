package com.yaelev.bank.service;

import com.yaelev.bank.model.Customer;
import com.yaelev.bank.model.TransactionAccount;
import com.yaelev.bank.repository.TransactionAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public TransactionAccount updateTransactionAccount(long id, TransactionAccount transactionAccount) {

        TransactionAccount existingTransactionAccount = transactionAccountRepository.findById(id).get();
        // orElseThrow( () -> new IllegalStateException("No transaction account with id " + id));

        Optional<TransactionAccount> foundByAccountNo = transactionAccountRepository
                .findTrAccountByAccountNo(transactionAccount.getAccountNo());
        if (foundByAccountNo.isPresent()) {
            throw new IllegalStateException(transactionAccount.getAccountNo() + " is used by another user");
        } else if (transactionAccount.getAccountNo().isEmpty()) {
            throw new IllegalStateException("Account number field is empty");
        } else {
            existingTransactionAccount.setAccountNo(transactionAccount.getAccountNo());
        }

        // Balance is checked in TransactionAccountClass
        existingTransactionAccount.setBalance(transactionAccount.getBalance());

        if (existingTransactionAccount.getCustomer() != null) {
        existingTransactionAccount.setCustomer(transactionAccount.getCustomer());
        } else {
            throw new IllegalStateException("Customer field is empty");
        }

        transactionAccountRepository.save(existingTransactionAccount);
        return existingTransactionAccount;
    }
}
