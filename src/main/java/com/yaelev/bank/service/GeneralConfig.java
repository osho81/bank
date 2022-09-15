package com.yaelev.bank.service;

import com.yaelev.bank.model.AppUser;
import com.yaelev.bank.model.Customer;
import com.yaelev.bank.model.Role;
import com.yaelev.bank.model.TransactionAccount;
import com.yaelev.bank.repository.CustomerRepository;
import com.yaelev.bank.repository.TransactionAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

// Note: also using repos instead of services; for development phase


@Configuration
public class GeneralConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                        TransactionAccountRepository transactionAccountRepository,
                                        AppUserRoleService appUserRoleService) {
        return args -> {
            Customer donald = new Customer(
                    "Donald",
                    "Duck",
                    LocalDate.of(1990, Month.MAY, 5),
                    "900505-7376",
                    "Second street 1",
                    "dduck@gmail.com",
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
            customerRepository.saveAll(List.of(donald, mickey, minnie));

            TransactionAccount transactionAccount1 = new TransactionAccount("741741789", 0, donald);

            TransactionAccount transactionAccount2 = new TransactionAccount("632563789", 0, mickey);

            TransactionAccount transactionAccount3 = new TransactionAccount("123456789", 0, mickey);

            transactionAccountRepository.saveAll(List.of(transactionAccount1, transactionAccount2, transactionAccount3));


            // Create temporary users, roles and add roles to users
            appUserRoleService.saveRole(new Role("ROLE_USER"));
            appUserRoleService.saveRole(new Role("ROLE_EMPLOYEE"));
            appUserRoleService.saveRole(new Role("ROLE_ADMIN"));

//            appUserRoleService.saveAppUser(new AppUser("user1", "123456"));
//            appUserRoleService.saveAppUser(new AppUser("user2", "234567"));
//            appUserRoleService.saveAppUser(new AppUser("user3", "345678"));
//
//            appUserRoleService.addAppUserRole("user1", "ROLE_USER");
//            appUserRoleService.addAppUserRole("user1", "ROLE_ADMIN");
//            appUserRoleService.addAppUserRole("user2", "ROLE_USER");
//            appUserRoleService.addAppUserRole("user3", "ROLE_ADMIN");
//            appUserRoleService.addAppUserRole("user3", "ROLE_EMPLOYEE");


            // Version after connecting Customer table to AppUser table via email
            appUserRoleService.saveAppUser(new AppUser("dduck@gmail.com", "123456"));
            appUserRoleService.saveAppUser(new AppUser("mickey@gmail.com", "234567"));
            appUserRoleService.saveAppUser(new AppUser("minnie@gmail.com", "345678"));

            appUserRoleService.addAppUserRole("dduck@gmail.com", "ROLE_USER");
            appUserRoleService.addAppUserRole("dduck@gmail.com", "ROLE_ADMIN");
            appUserRoleService.addAppUserRole("mickey@gmail.com", "ROLE_USER");
            appUserRoleService.addAppUserRole("minnie@gmail.com", "ROLE_ADMIN");
            appUserRoleService.addAppUserRole("minnie@gmail.com", "ROLE_EMPLOYEE");

        };

    }

}
