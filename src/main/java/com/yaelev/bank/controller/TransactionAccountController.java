package com.yaelev.bank.controller;

import com.yaelev.bank.model.TransactionAccount;
import com.yaelev.bank.repository.TransactionAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/v1/t-account")
public class TransactionAccountController {
    private final TransactionAccountRepository transactionAccountRepository;

    @Autowired
    public TransactionAccountController(TransactionAccountRepository transactionAccountRepository) {
        this.transactionAccountRepository = transactionAccountRepository;
    }

    @GetMapping("/all")
    public List<TransactionAccount> transactionAccounts() {
        return transactionAccountService.getCustomers();
    }

}
