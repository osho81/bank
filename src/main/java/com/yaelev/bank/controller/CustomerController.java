package com.yaelev.bank.controller;

import com.yaelev.bank.model.Customer;
import com.yaelev.bank.repository.CustomerRepository;
import com.yaelev.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// "Api layer"; controls endpoints, routing etc.
// REST API.
// (while service have more specific methods such as CRUD operations)

// Solving frontend cors-policy for api requests
@CrossOrigin(origins = "http://localhost:3000") // points to react (spring port: 8080)
@RestController // Rest API component: https://spring.io/guides/tutorials/rest/
@RequestMapping(path = "/api/v1/customer") // i.e. http://localhost:8080/api/v1/customer
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerRepository customerRepository;

    // Autowire constructor, OR autowire declaration of the objects above
    @Autowired
    // "auto-instantiate" customerservice (skip new-keyword)
    // Add @Component or @Service in CustomerService class
    // These "auto-instantiating" annotations is type of "dependency injection".
    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/all") // http://localhost:8080/api/v1/customer/all
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok().body(customerService.getCustomers());
    }

    @GetMapping("/{id}") // http://localhost:8080/api/v1/customer/id-arg
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        return customerService.getCustomerById(id); // Extract id from path
    }

    @PostMapping // @RequestBody is for input
    public void registerNewCustomer(@RequestBody Customer customer) {
        customerService.registerNewCustomer(customer);
    }

    @PutMapping(path = "/{id}") // http://localhost:8080/api/v1/customer/id-arg
    public void updateProduct(@PathVariable("id") long id, @RequestBody Customer customer) {
        System.out.println("hello");
        System.out.println(id);
        customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}") // http://localhost:8080/api/v1/customer/id-arg
    public void deleteCustomer(@PathVariable long id) { // extracts id-part
        customerService.deleteCustomer(id); // Pass in the extracted id
    }

}
