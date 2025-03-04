package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> listCustomers();
    CustomerDTO getCustomerById(UUID id);

    CustomerDTO createCustomer(CustomerDTO newCustomerDTO);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO updatedCustomerDTO);

    Optional<CustomerDTO> deleteCustomerId(UUID id);

    Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO updatedCustomerDTO);
}
