package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();
    Customer getCustomerById(UUID id);

    Customer createCustomer(Customer newCustomer);

    Optional<Customer> updateCustomerById(UUID customerId, Customer updatedCustomer);

    Optional<Customer> deleteCustomerId(UUID id);

    Optional<Customer> patchCustomerById(UUID customerId, Customer updatedCustomer);
}
