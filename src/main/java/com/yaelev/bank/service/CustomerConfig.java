// MOVED TO MAIN CONFIG FILE

//package com.yaelev.bank.service;
//
//// Class for adding data to Customer table
//
//import com.yaelev.bank.model.Customer;
//import com.yaelev.bank.model.TransactionAccount;
//import com.yaelev.bank.repository.CustomerRepository;
//import com.yaelev.bank.repository.TransactionAccountRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class CustomerConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner (CustomerRepository repository) {
//        return args -> {
//            Customer donald = new Customer( // Skip id, since it is auto-generated
//                    "Donald",
//                    "Duck",
//                    LocalDate.of(1990, Month.MAY, 5),
//                    "900505-7376",
//                    "Second street 1",
//                    "donaldduck@gmail.com",
//                    null
//            );
//
//            Customer mickey = new Customer(
//                    "Mickey",
//                    "Mouse",
//                    LocalDate.of(1980, Month.JUNE, 6),
//                    "800606-4175",
//                    "First street 1",
//                    "mickey@gmail.com",
//                    null
//            );
//
//            Customer minnie = new Customer(
//                    "Minnie",
//                    "Mouse",
//                    LocalDate.of(1985, Month.JULY, 7),
//                    "850707-5565",
//                    "First street 2",
//                    "minnie@gmail.com",
//                    null
//            );
//
//            repository.saveAll(List.of(donald, mickey, minnie)
//            );
//        };
//    }
//
//    @Bean
//    CommandLineRunner secondCommandLineRunner (TransactionAccountRepository repository) {
//        return args -> {
//
//            TransactionAccount transactionAccount1 = new TransactionAccount("741741789", 0, null);
//
//            TransactionAccount transactionAccount2 = new TransactionAccount("632563789", 0, null);
//
//            TransactionAccount transactionAccount3 = new TransactionAccount("123456789", 0, null);
//
//            repository.saveAll(List.of(transactionAccount1, transactionAccount2, transactionAccount3)
//            );
//        };
//    }
//
//}
