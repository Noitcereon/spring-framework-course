package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.models.CustomerDTO;
import me.noitcereon.practice.spring6restmvc.repositories.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class CustomerControllerIntegrationTest {

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerRepo customerRepo;

    @Test
    void fetchAllCustomersReturns3Customers() {
        List<CustomerDTO> customerDtos = customerController.listCustomers();

        assertEquals(3, customerDtos.size());
    }

    @Rollback
    @Transactional
    @Test
    void fetchAllCustomersReturns0CustomerWhenNoData() {
        customerRepo.deleteAll();

        List<CustomerDTO> customerDtos = customerController.listCustomers();

        assertEquals(0, customerDtos.size());
    }

    @Test
    void fetchCustomerByKnownIdReturnsCustomer() {
        UUID existingCustomerId = customerRepo.findAll().getFirst().getId();

        Optional<CustomerDTO> customerDto = customerController.getCustomerById(existingCustomerId);

        assertEquals(existingCustomerId, customerDto.orElseThrow().getId());
    }

    @Test
    void fetchCustomerByUnknownIdReturnsEmptyOptional() {
        UUID unknownCustomerId = UUID.randomUUID();

        Optional<CustomerDTO> customerDto = customerController.getCustomerById(unknownCustomerId);

        assertTrue(customerDto.isEmpty());
    }
}