package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.models.Customer;
import me.noitcereon.practice.spring6restmvc.services.CustomerService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    public static final String BASE_URL = "/api/v1/customer";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

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
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.createCustomer(customer);
        return ResponseEntity
                .created(URI.create("/api/v1/customer/" + newCustomer.getId()))
                .build();
    }

    @PutMapping("{customerId}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable UUID customerId, @RequestBody Customer updatedCustomer) {
        Optional<Customer> savedCustomer = customerService.updateCustomerById(customerId, updatedCustomer);
        if (savedCustomer.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(savedCustomer.get());
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity deleteBeerById(@PathVariable("customerId") UUID id) {
        Optional<Customer> deletedCustomer = customerService.deleteCustomerId(id);
        if (deletedCustomer.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<Customer> patchCustomerById(@PathVariable UUID customerId, @RequestBody Customer updatedCustomer) {
        Optional<Customer> savedCustomer = customerService.patchCustomerById(customerId, updatedCustomer);
        if (savedCustomer.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(savedCustomer.get());
    }
}
