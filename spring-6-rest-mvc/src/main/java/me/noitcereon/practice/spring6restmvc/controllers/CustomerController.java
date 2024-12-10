package me.noitcereon.practice.spring6restmvc.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.noitcereon.practice.spring6restmvc.models.Customer;
import me.noitcereon.practice.spring6restmvc.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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
    @PostMapping
    public ResponseEntity createCustomer(@RequestBody Customer customer){
        Customer newCustomer = customerService.createCustomer(customer);
        return ResponseEntity
                .created(URI.create("/api/v1/customer/" + newCustomer.getId()))
                .build();
    }
    @PutMapping("{customerId}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable UUID customerId, @RequestBody Customer updatedCustomer){
        Optional<Customer> savedCustomer = customerService.updateCustomerById(customerId, updatedCustomer);
        if(savedCustomer.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(savedCustomer.get());
    }
}
