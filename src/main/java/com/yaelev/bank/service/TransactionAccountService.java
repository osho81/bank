package com.yaelev.bank.service;

import com.yaelev.bank.model.TransactionAccount;
import com.yaelev.bank.repository.TransactionAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionAccountService {

    private final TransactionAccountRepository transactionAccountRepository;

    @Autowired
    public TransactionAccountService(TransactionAccountRepository transactionAccountRepository) {
        this.transactionAccountRepository = transactionAccountRepository;
    }

    public List<TransactionAccount> getCustomers() {
       return (List<TransactionAccount>) transactionAccountRepository.findAll();
    }
}
