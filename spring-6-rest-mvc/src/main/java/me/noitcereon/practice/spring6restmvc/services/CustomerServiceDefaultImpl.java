package me.noitcereon.practice.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import me.noitcereon.practice.spring6restmvc.entities.Customer;
import me.noitcereon.practice.spring6restmvc.mappers.CustomerMapper;
import me.noitcereon.practice.spring6restmvc.models.CustomerDTO;
import me.noitcereon.practice.spring6restmvc.repositories.CustomerRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Primary
@Service
public class CustomerServiceDefaultImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepo customerRepo;

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers.stream().map(customerMapper::customerToCustomerDTO).toList();
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        Optional<Customer> customer = customerRepo.findById(id);
        return customer.map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO newCustomerDTO) {
        return null;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO updatedCustomerDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<CustomerDTO> deleteCustomerId(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO updatedCustomerDTO) {
        return Optional.empty();
    }
}
