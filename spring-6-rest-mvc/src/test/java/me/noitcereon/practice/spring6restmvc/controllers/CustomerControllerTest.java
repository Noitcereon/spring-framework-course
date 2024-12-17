package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.models.Customer;
import me.noitcereon.practice.spring6restmvc.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc; // used to "call the endpoints" in the CustomerController.

    @MockitoBean
    CustomerService mockCustomerService;

    @Test
    void givenCustomerId_whenFetchingCustomerById_thenTheCustomerWithTheGivenIdIsReturnedAsJson() throws Exception {
        // Arrange
        Customer expectedCustomer = Customer.builder().id(UUID.randomUUID()).customerName("Eragon Shadeslayer").build();
        when(mockCustomerService.getCustomerById(expectedCustomer.getId()))
                .thenReturn(expectedCustomer);
        // Act
        String endpoint = "/api/v1/customer/{customerId}"; // {customerId} is replaced in the MockMvcRequestBuilders.get() method by the additional argument provided to the method.
        ResultActions performResult = mockMvc.perform(MockMvcRequestBuilders.get(endpoint, expectedCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        performResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedCustomer.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value(expectedCustomer.getCustomerName()));
    }

    @Test
    void givenCustomerService_whenFetchingCustomerList_thenCustomerListIsReturnedAsJson() throws Exception {
        // Arrange
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(Customer.builder().id(UUID.randomUUID()).customerName("Eragon Shadeslayer").build());
        expectedCustomers.add(Customer.builder().id(UUID.randomUUID()).customerName("Will Treaty").build());
        when(mockCustomerService.listCustomers()).thenReturn(expectedCustomers);
        String endpoint = "/api/v1/customer";

        // Act
        ResultActions performResult = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        performResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedCustomers.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(expectedCustomers.get(0).getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerName").value(expectedCustomers.get(0).getCustomerName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(expectedCustomers.get(1).getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].customerName").value(expectedCustomers.get(1).getCustomerName()));
    }
}