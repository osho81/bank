package com.yaelev.bank.service;

// Class for adding data to Customer table

import com.yaelev.bank.model.Customer;
import com.yaelev.bank.model.TransactionAccount;
import com.yaelev.bank.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner (CustomerRepository repository) {
        return args -> {
            Customer donald = new Customer( // Skip id, since it is auto-generated
                    "Donald",
                    "Duck",
                    LocalDate.of(1990, Month.MAY, 5),
                    "900505-7376",
                    "Second street 1",
                    "donaldduck@gmail.com"
            );

            Customer mickey = new Customer(
                    "Mickey",
                    "Mouse",
                    LocalDate.of(1980, Month.JUNE, 6),
                    "800606-4175",
                    "First street 1",
                    "mickey@gmail.com"
            );

            Customer minnie = new Customer(
                    "Minnie",
                    "Mouse",
                    LocalDate.of(1985, Month.JULY, 7),
                    "850707-5565",
                    "First street 2",
                    "minnie@gmail.com"
            );

            repository.saveAll(List.of(donald, mickey, minnie)
            );
        };
    }

}
