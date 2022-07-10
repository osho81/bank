package com.yaelev.bank.customer;

import org.springframework.data.repository.CrudRepository;

// "Data access layer"; enables CRUD operations; used by related service class

// Specify class to service with CRUD methods etc., and specify Id type
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    // Customizedd operations and queries


}

