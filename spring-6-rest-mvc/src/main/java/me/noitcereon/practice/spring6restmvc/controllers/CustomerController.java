package me.noitcereon.practice.spring6restmvc.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.noitcereon.practice.spring6restmvc.models.Customer;
import me.noitcereon.practice.spring6restmvc.services.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> listCustomers() {
        log.info("List customers called");
        return customerService.listCustomers();
    }
    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable UUID customerId) {
        log.info("Get customer by id called with id: {}", customerId);
        return customerService.getCustomerById(customerId);
    }
}
