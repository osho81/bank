package com.yaelev.bank.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

// "Service layer"; between Api and data access;
// Services such as CRUD operations.

@Service // Enables @Autowired in this class' references in other classes
// @Component is similar to @Service but Service is more specific to this usage
public class CustomerService {

    // Implements related interface
    // (spring enables skipping implement keyword: https://spring.io/guides/gs/accessing-data-jpa/
    private final CustomerRepository customerRepository;

    @Autowired // Autowire/inject the implemented interface
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }


    // Test method before db is connected and mapped; testing web functions
/*    public List<Customer> getCustomers() {
        return List.of(new Customer(10, "Donald", "Duck", LocalDate.of(1990, Month.MAY, 5),
                "900505-7346", "Second street 1", "donaldduck@gmail.com"));
    }*/
}
