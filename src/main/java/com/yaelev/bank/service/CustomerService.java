package com.yaelev.bank.service;

import com.yaelev.bank.model.Customer;
import com.yaelev.bank.model.TransactionAccount;
import com.yaelev.bank.repository.CustomerRepository;
import com.yaelev.bank.repository.TransactionAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// (Optional) "Service layer"; between Api and data access;
// Services such as assisting CRUD operations.
// Suitable for filtering or validating request results etc.

@Service // Enables @Autowired in this class' references in other classes
// @Component is similar to @Service but Service is more specific for this purpose
public class CustomerService {

    // Implements the interface CustomerRepository
    // (spring enables skipping "implement" keyword: https://spring.io/guides/gs/accessing-data-jpa/ )
    private final CustomerRepository customerRepository;

    // TransactionAccountRepository/Service needed for deletion, de-association of children:
    private final TransactionAccountRepository transactionAccountRepository;
    // private final TransactionAccountService transactionAccountService;

    @Autowired // Autowire/inject the implemented interface
    public CustomerService(CustomerRepository customerRepository,
                                   TransactionAccountRepository transactionAccountRepository) {
        this.customerRepository = customerRepository;

        // Needed for deletion, de-association of children:
        this.transactionAccountRepository = transactionAccountRepository;
        // this.transactionAccountService = transactionAccountService;
    }



    //////////////////////////// READ //////////////////////////

    // Get all customers
    public List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    // Get specific customer, find by customer id
    public ResponseEntity<Customer> getCustomerById(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Customer with id " + id + " doesn't exist"));
        return ResponseEntity.ok(customer);
    }


    //////////////////////////// CREATE //////////////////////////

    // TODO: add automatic creation/update of connected AppUser in create method
    // (and update method if needed)

    public void registerNewCustomer(Customer customer) {
        Optional<Customer> foundByEmail = customerRepository.findByEmail(customer.getEmail());
        if (foundByEmail.isPresent()) { // If returned container is not empty...
            throw new IllegalStateException(customer.getEmail() + " is used by another user");
        } else {
            System.out.println("New customer added; " + customer);
            customerRepository.save(customer);
        }
    }


    //////////////////////////// UPDATE //////////////////////////

    @Transactional
    public Customer updateCustomer(long id, Customer customer) {

        // Check whether customer with given id exists in DB or not
        Customer existingCustomer = customerRepository.findById(id).get();
        //OR: .orElseThrow( () -> new IllegalStateException("No customer with id " + id));

        // Check if the email is already used by any user
        Optional<Customer> foundByEmail = customerRepository.findByEmail(customer.getEmail());
        if (foundByEmail.isPresent()) {
            throw new IllegalStateException(customer.getEmail() + " is used by another user");
        } else if (customer.getEmail().isEmpty()) {

            // Compulsory here fields is OK, since frontend passes in previous values when needed
            throw new IllegalStateException("Email field is empty");
        } else {
            existingCustomer.setEmail(customer.getEmail());
        }

        // Check if the other fields/columns are not empty or null (regardless if are nullable in db)
        if (!customer.getfName().isEmpty() && customer.getfName() != null) {
            existingCustomer.setfName(customer.getfName());
        } else {
            throw new IllegalStateException("First name field is empty");
        }
        if (!customer.getlName().isEmpty() && customer.getlName() != null) {
            existingCustomer.setlName(customer.getlName());
        } else {
            throw new IllegalStateException("Last name field is empty");
        }
        if (!customer.getSsn().isEmpty() && customer.getSsn() != null) {
            existingCustomer.setSsn(customer.getSsn());
        } else {
            throw new IllegalStateException("SSN field is empty");
        }
        if (!customer.getAddress().isEmpty() && customer.getAddress() != null) {
            existingCustomer.setAddress(customer.getAddress());
        } else {
            throw new IllegalStateException("Address field is empty");
        }
        // Frontend side will provide more sufficient input limit of date format
        if (customer.getDateOfBirth() != null) {
            existingCustomer.setDateOfBirth(customer.getDateOfBirth());
        } else {
            throw new IllegalStateException("Date of birth field is empty");
        }

        // save existing customer to database
        customerRepository.save(existingCustomer);
        return existingCustomer;
    }


    //////////////////////////// DELETE //////////////////////////

    public void deleteCustomer(long id) {
        Customer disassociatedCustomer = disassociateAccounts(id);
        customerRepository.deleteById(disassociatedCustomer.getId());
    }


    // Method for removing relation between customer and accounts
    // (Could also disable fk constraint checks)
    private Customer disassociateAccounts(long id) {
        Customer customer = customerRepository.findById(id).get();
        List<TransactionAccount> associatedAccounts = customer.getTransactionAccounts();
        for (TransactionAccount acc : associatedAccounts) {
            TransactionAccount currentAcc = transactionAccountRepository.findById(acc.getId()).get();
            currentAcc.setCustomer(null);
            transactionAccountRepository.save(currentAcc);
        }

        return customerRepository.save(customer);
    }

}
