package com.yaelev.bank.service;

import com.yaelev.bank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

// (Optional) "Service layer"; between Api and data access;
// Services such as assisting CRUD operations.
// Suitable for filtering results etc.

@Service // Enables @Autowired in this class' references in other classes
// @Component is similar to @Service but Service is more specific for this purpose
public class CustomerService {

    // Implements the interface CustomerRepository
    // (spring enables skipping "implement" keyword: https://spring.io/guides/gs/accessing-data-jpa/ )
    private final CustomerRepository customerRepository;

    @Autowired // Autowire/inject the implemented interface
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Get all customers
    public List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    // Get specific customer, find by customer id
    public ResponseEntity<Customer> getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new IllegalStateException("Customer with id " + customerId + " doesn't exist"));
        return ResponseEntity.ok(customer);
    }

    public void registerNewCustomer(Customer customer) {
        Optional<Customer> foundByEmail = customerRepository.findCustomerByEmail(customer.getEmail());
        if (foundByEmail.isPresent()) { // If returned container is not empty...
            throw new IllegalStateException(foundByEmail + " is used by another user");
        } else {
            System.out.println("new customer added; " + customer);
            customerRepository.save(customer);
        }
    }

    // @Transactional // JPA/hibernate management state
    public void updateCustomer(Long customerId, @RequestBody Customer customer, String email, String address) {
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);
        if (foundCustomer.isPresent()) { // If returned container is not empty...

            if (customer.getEmail() != null && !Objects.equals(customer.getEmail(), email)) {
                // AND if email is not used by another customer, then set new email
                Optional<Customer> foundEmail = customerRepository.findCustomerByEmail(customer.getEmail());
                if (foundEmail.isPresent()) {
                    throw new IllegalStateException(foundEmail + " is used by another user");
                } else {
                    customer.setEmail(customer.getEmail());
                }
            }

            // If address is entered & it is not as previous address, then set the new address
            if (customer.getAddress() != null && !Objects.equals(customer.getAddress(), address)) {
                customer.setAddress(customer.getAddress());
            }

            customerRepository.save(customer);

        } else { // if user doesn't exists...

            throw new IllegalStateException("Customer with id " + customerId + " doesn't exist");
        }

    }

    public void deleteCustomer(Long customerId) {
        Optional<Customer> customerExists = customerRepository.findById(customerId);
        if (customerExists.isPresent()) { // If returned container is not empty...
            customerRepository.deleteById(customerId);
        } else {
            throw new IllegalStateException("Customer with id " + customerId + " doesn't exist");
        }

    }

}
