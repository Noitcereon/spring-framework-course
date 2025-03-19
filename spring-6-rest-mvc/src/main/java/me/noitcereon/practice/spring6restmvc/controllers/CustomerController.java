package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.models.CustomerDTO;
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
    public List<CustomerDTO> listCustomers() {
        log.info("List customers called");
        return customerService.listCustomers();
    }

    @GetMapping("/{customerId}")
    public Optional<CustomerDTO> getCustomerById(@PathVariable UUID customerId) {
        log.info("Get customer by id called with id: {}", customerId);
        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO newCustomerDTO = customerService.createCustomer(customerDTO);
        return ResponseEntity
                .created(URI.create("/api/v1/customer/" + newCustomerDTO.getId()))
                .body(newCustomerDTO);
    }

    @PutMapping("{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomerById(@PathVariable UUID customerId, @RequestBody CustomerDTO updatedCustomerDTO) {
        Optional<CustomerDTO> savedCustomer = customerService.updateCustomerById(customerId, updatedCustomerDTO);
        if (savedCustomer.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(savedCustomer.get());
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("customerId") UUID id) {
        Optional<CustomerDTO> deletedCustomer = customerService.deleteCustomerId(id);
        if (deletedCustomer.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> patchCustomerById(@PathVariable UUID customerId, @RequestBody CustomerDTO updatedCustomerDTO) {
        Optional<CustomerDTO> savedCustomer = customerService.patchCustomerById(customerId, updatedCustomerDTO);
        if (savedCustomer.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(savedCustomer.get());
    }
}
