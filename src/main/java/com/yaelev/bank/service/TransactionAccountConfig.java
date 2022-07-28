package com.yaelev.bank.service;

import com.yaelev.bank.model.Customer;
import com.yaelev.bank.model.TransactionAccount;
import com.yaelev.bank.repository.TransactionAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class TransactionAccountConfig {

    @Bean
    CommandLineRunner secondCommandLineRunner (TransactionAccountRepository repository) {
        return args -> {

            TransactionAccount transactionAccount1 = new TransactionAccount("741741789");

            TransactionAccount transactionAccount2 = new TransactionAccount("632563789");

            TransactionAccount transactionAccount3 = new TransactionAccount("123456789");

            repository.saveAll(List.of(transactionAccount1, transactionAccount2, transactionAccount3)
            );
        };
    }

}
