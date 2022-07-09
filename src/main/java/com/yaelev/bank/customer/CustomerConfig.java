package com.yaelev.bank.customer;

// Class for adding data to Customer table

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner (CustomerRepository repository) {
        return args -> {
            Customer donald = new Customer( // Skip id, since it is auto-generated
                    1001,
                    "Donald",
                    "Duck",
                    LocalDate.of(1990, Month.MAY, 5),
                    "900505-7376",
                    "Second street 1",
                    "donaldduck@gmail.com"
            );

            Customer mickey = new Customer(
                    2002,
                    "Mickey",
                    "Mouse",
                    LocalDate.of(1980, Month.JUNE, 6),
                    "800606-4175",
                    "First street 1",
                    "mickey@gmail.com"
            );

            Customer minnie = new Customer(
                    2007,
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
