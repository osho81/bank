package com.yaelev.bank.customer;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

// "Service layer"; between Api and data access.

@Service // Enables @Autowired in this class' references in other classes
// @Component is similar to @Service but Service is more specific to this usage
public class CustomerService {
    public List<Customer> getCustomers() {
        return List.of(new Customer(10, "Donald", "Duck", LocalDate.of(1990, Month.MAY, 5),
                "900505-7346", "Second street 1", "donaldduck@gmail.com"));
    }
}
