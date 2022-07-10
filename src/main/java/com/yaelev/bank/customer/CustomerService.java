package com.yaelev.bank.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

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

    public void addNewCustomer(Customer customer) {
        Optional<Customer> foundEmail = customerRepository.findCustomerByEmail(customer.getEmail());
        if (foundEmail.isPresent()) { // If returned container is not empty...
            throw new IllegalStateException(foundEmail + " is used by another user");
        } else {
            System.out.println("new customer added; " + customer);
            customerRepository.save(customer);
        }
    }

    public void removeCustomer(Long customerId){
        Optional<Customer> customerExists = customerRepository.findById(customerId);
        if (customerExists.isPresent()) { // If returned container is not empty...
            customerRepository.deleteById(customerId);
        } else {
            throw new IllegalStateException("Customer with id " + customerId + " doesn't exist");
        }

    }

}
