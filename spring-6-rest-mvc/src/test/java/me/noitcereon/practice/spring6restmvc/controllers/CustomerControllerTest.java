package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerTest {

    @Autowired
    CustomerController customerController;
    @Test
    void givenNothing_WhenGettingAllCustomers_ANonEmptyListIsReturned(){
        List<Customer> actual = customerController.listCustomers();

        assertTrue(actual.size() > 0);
    }
    @Test
    void givenCustomerId_WhenFetchingSpecificCustomer_ThatCustomerIsReturned(){
        Customer actual = customerController.getCustomerById(UUID.fromString("eae5e6d4-d511-4200-b5d7-73c485701c41"));
        String expectedCustomerName = "Eragon Shadeslayer";

        assertEquals(expectedCustomerName, actual.getCustomerName());
    }
    @Test
    void givenCustomer_WhenCreatingNewCustomer_ThenReturnCreatedStatus(){
        HttpStatus expected = HttpStatus.CREATED;
        Customer testCustomer = Customer.builder().customerName("John Doe").build();
        ResponseEntity actual = customerController.createCustomer(testCustomer);
        assertEquals(expected, actual.getStatusCode());
    }
}