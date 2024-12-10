package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();
    Customer getCustomerById(UUID id);
}
