package com.yaelev.bank.service;

import com.yaelev.bank.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// "Data access layer"; enables CRUD operations; used by related service class

// Specify class to service with CRUD methods etc., and specify Id type
public interface CustomerRepository extends CrudRepository<Customer, Long> {


    // Customized operations and queries

    // @Query("select c from Customer c where c.email = ?1")
    Optional<Customer> findCustomerByEmail(String email);

}

