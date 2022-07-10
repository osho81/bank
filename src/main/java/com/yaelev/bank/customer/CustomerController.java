package com.yaelev.bank.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// "Api layer"; controls endpoints, routing etc.
// (while service have more specific methods such as CRUD operations)

@RestController // Rest API component: https://spring.io/guides/tutorials/rest/
@RequestMapping(path = "/start") // Either use this main routing for whole class or specify at endpoint methods
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

    @GetMapping //("customers/list") // Either use these endpoint routings or main RequestMapping above
    public List<Customer> customers() {
        return customerService.getCustomers();
    }

    @PostMapping // @RequestBody is for input
    public void registerNewCustomer(@RequestBody Customer customer) {
        customerService.addNewCustomer(customer);
    }


}
