package com.yaelev.bank.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController // Rest API component: https://spring.io/guides/tutorials/rest/
@RequestMapping(path = "/start") // Either use this main routing for whole class or specify at endpoint methods
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    // "auto-instantiate" custoemrservice;, we dont need the keyword new etc)
    // also need to add @Component to CustomerService class
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Major types of Rest endpoints;
    // https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/html/production-ready-endpoints.html

    @GetMapping //("customers/list") // Either use these endpoint routings or main RequestMapping above
    public List<Customer> customers() {
        return customerService.getCustomers();
    }
}
