package com.yaelev.bank.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;
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

    public void registerNewCustomer(Customer customer) {
        Optional<Customer> foundEmail = customerRepository.findCustomerByEmail(customer.getEmail());
        if (foundEmail.isPresent()) { // If returned container is not empty...
            throw new IllegalStateException(foundEmail + " is used by another user");
        } else {
            System.out.println("new customer added; " + customer);
            customerRepository.save(customer);
        }
    }

    public void deleteCustomer(Long customerId){
        Optional<Customer> customerExists = customerRepository.findById(customerId);
        if (customerExists.isPresent()) { // If returned container is not empty...
            customerRepository.deleteById(customerId);
        } else {
            throw new IllegalStateException("Customer with id " + customerId + " doesn't exist");
        }

    }

    @Transient // No need to store anything in DB here
    public void updateCustomer(Long customerId, String email, String address) {
        // id to find customer
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
            new IllegalStateException("Customer with id " + customerId + " doesn't exist"));

        // If email is entered & it is not as previous email, set new email
        if (email != null && email.contains("@") &&
                !Objects.equals(customer.getEmail(), email)) {
            customer.setEmail(email);
        }

        // If address is entered & it is not as previous address, set new address
        if (email != null && email.contains("@") &&
                !Objects.equals(customer.getEmail(), email)) {
            customer.setEmail(email);
        }
    }

}
