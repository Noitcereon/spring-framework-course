package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerTest {

    @Autowired
    CustomerController customerController;
    @Test
    void givenController_WhenGettingAllCustomers_ANonEmptyListIsReturned(){
        List<Customer> actual = customerController.listCustomers();

        assertTrue(actual.size() > 0);
    }
    @Test
    void givenControllerAndCustomerId_WhenFetchingSpecificCustomer_ThatCustomerIsReturned(){
        Customer actual = customerController.getCustomerById(UUID.fromString("eae5e6d4-d511-4200-b5d7-73c485701c41"));
        String expectedCustomerName = "Eragon Shadeslayer";

        assertEquals(expectedCustomerName, actual.getCustomerName());
    }
}