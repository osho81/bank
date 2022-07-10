package com.yaelev.bank.customer;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// "Data access layer"; enables CRUD operations; used by related service class

// Specify class to service with CRUD methods etc., and specify Id type
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    // Customized operations and queries
    Optional<Customer> findCustomerByEmail(String email); // Container
}

