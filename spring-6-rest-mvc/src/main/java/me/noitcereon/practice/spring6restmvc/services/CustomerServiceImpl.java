package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, Customer> customers = new HashMap<>();

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

        customers.put(customer1.getId(), customer1);
        customers.put(customer2.getId(), customer2);
        customers.put(customer3.getId(), customer3);
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customers.get(id);
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
        customers.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }
}
