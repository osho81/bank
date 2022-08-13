package com.yaelev.bank.repository;

import com.yaelev.bank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

// "Data access layer"; enables CRUD operations; used by related service class

// Specify class to service with CRUD methods etc., and specify id type (Long)
public interface CustomerRepository extends JpaRepository<Customer, Long> { // Or CrudRepository


    // Customized operations and queries

    Optional<Customer> findCustomerByEmail(String email);

    // Compare e.g.:
    // @Query("select c from Customer c where c.email = ?1")

}

