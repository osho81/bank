package com.yaelev.bank.service;

import com.yaelev.bank.model.AppUser;
import com.yaelev.bank.model.Customer;
import com.yaelev.bank.model.Role;
import com.yaelev.bank.model.TransactionAccount;
import com.yaelev.bank.repository.AppUserRepository;
import com.yaelev.bank.repository.CustomerRepository;
import com.yaelev.bank.repository.RoleRepository;
import com.yaelev.bank.repository.TransactionAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

// Note: using repos instead of services; for temporary development phase


@Configuration
public class MainConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                        TransactionAccountRepository transactionAccountRepository,
                                        AppUserRepository appUserRepository,
                                        RoleRepository roleRepository) {
        return args -> {
            Customer donald = new Customer(
                    "Donald",
                    "Duck",
                    LocalDate.of(1990, Month.MAY, 5),
                    "900505-7376",
                    "Second street 1",
                    "donaldduck@gmail.com",
                    null
            );

            Customer mickey = new Customer(
                    "Mickey",
                    "Mouse",
                    LocalDate.of(1980, Month.JUNE, 6),
                    "800606-4175",
                    "First street 1",
                    "mickey@gmail.com",
                    null
            );

            Customer minnie = new Customer(
                    "Minnie",
                    "Mouse",
                    LocalDate.of(1985, Month.JULY, 7),
                    "850707-5565",
                    "First street 2",
                    "minnie@gmail.com",
                    null
            );
            customerRepository.saveAll(List.of(donald, mickey, minnie)
            );

            TransactionAccount transactionAccount1 = new TransactionAccount("741741789", 0, donald);

            TransactionAccount transactionAccount2 = new TransactionAccount("632563789", 0, mickey);

            TransactionAccount transactionAccount3 = new TransactionAccount("123456789", 0, mickey);

            transactionAccountRepository.saveAll(List.of(transactionAccount1, transactionAccount2, transactionAccount3)
            );

            // Create temporary users and roles (for the development phase)

            // Role role1 = new Role("ROLE_USER");
            roleRepository.save(new Role("ROLE_USER"));
            roleRepository.save(new Role("ROLE_EMPLOYEE"));
            roleRepository.save(new Role("ROLE_ADMIN"));

            // AppUser appUser1 = new AppUser("TestingUser1", "testingKing1", "123456");
            appUserRepository.save(new AppUser("AppUser1", "user1", "123456"));
            appUserRepository.save(new AppUser("AppUser2", "user2", "234567"));
            appUserRepository.save(new AppUser("AppUser3", "user3", "345678"));



        };
    }

}
