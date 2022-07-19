package com.yaelev.bank.controller;

import com.yaelev.bank.model.Customer;
import com.yaelev.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// "Api layer"; controls endpoints, routing etc.
// (while service have more specific methods such as CRUD operations)

// Solving frontend cors policy for api requests
@CrossOrigin(origins = "http://localhost:3000")
@RestController // Rest API component: https://spring.io/guides/tutorials/rest/
@RequestMapping(path = "/api/v1") // Align with GET endpoint
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    // "auto-instantiate" customerservice (we dont need the keyword new etc)
    // also need to add @Component or @Service in CustomerService class
    // These "auto-instantiating" annotations is type of "dependency injection".
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Major types of Rest endpoints;
    // https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/html/production-ready-endpoints.html

    @GetMapping ("/customers") // Align with @RequestMapping above
    public List<Customer> customers() {
        return customerService.getCustomers();
    }

    @PostMapping // @RequestBody is for input
    public void registerNewCustomer(@RequestBody Customer customer) {
        customerService.registerNewCustomer(customer);
    }

    @DeleteMapping(path = "{customerId}") // i.e.: URL/id
    public void deleteCustomer(@PathVariable("customerId") Long customerId) { // extracts id-part
        customerService.deleteCustomer(customerId); // Operate on the extracted id-part
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer( // Extract id, and optional email & address paras
            @PathVariable("customerId") Long customerId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String address) {
        // id to find customer; email/address to change if customer exists
        customerService.updateCustomer(customerId, email, address);

    }

}
