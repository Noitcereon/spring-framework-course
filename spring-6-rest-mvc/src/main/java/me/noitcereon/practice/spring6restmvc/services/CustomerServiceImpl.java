package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.Customer;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Noit Cereon")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        Customer customer2 = Customer.builder()
                .id(UUID.fromString("eae5e6d4-d511-4200-b5d7-73c485701c41"))
                .customerName("Eragon Shadeslayer")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Will Treaty")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        customerMap = new HashMap<>();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public Customer createCustomer(Customer newCustomer) {
        LocalDateTime now = LocalDateTime.now();
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .version(newCustomer.getVersion())
                .customerName(newCustomer.getCustomerName())
                .createdDate(now)
                .lastModifiedDate(now)
                .build();
        customerMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public Optional<Customer> updateCustomerById(UUID customerId, Customer updatedCustomer) {
        Customer customerToBeUpdated = customerMap.get(customerId);
        if (customerToBeUpdated == null) {
            log.warn("Could not find customer with id '" + customerId + "'");
            return Optional.empty();
        }
        customerToBeUpdated.setCustomerName(updatedCustomer.getCustomerName());
        customerToBeUpdated.setLastModifiedDate(LocalDateTime.now());
        return Optional.of(customerToBeUpdated);
    }

    @Override
    public Optional<Customer> deleteCustomerId(UUID id) {
        Customer existingCustomer = customerMap.remove(id);
        return Optional.ofNullable(existingCustomer);
    }

    @Override
    public Optional<Customer> patchCustomerById(UUID customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerMap.get(customerId);
        if (existingCustomer == null) return Optional.empty();
        boolean wasUpdated = false;

        if (StringUtils.hasText(updatedCustomer.getCustomerName())) {
            existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
            wasUpdated = true;
        }
        if (wasUpdated) {
            existingCustomer.setLastModifiedDate(LocalDateTime.now());
        }
        return Optional.of(existingCustomer);
    }
}
