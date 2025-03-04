package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.controllers.exception.NotFoundException;
import me.noitcereon.practice.spring6restmvc.models.CustomerDTO;
import me.noitcereon.practice.spring6restmvc.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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
        CustomerDTO expectedCustomerDTO = CustomerDTO.builder().id(UUID.randomUUID()).customerName("Eragon Shadeslayer").build();
        when(mockCustomerService.getCustomerById(expectedCustomerDTO.getId()))
                .thenReturn(expectedCustomerDTO);
        // Act
        String endpoint = "/api/v1/customer/{customerId}"; // {customerId} is replaced in the MockMvcRequestBuilders.get() method by the additional argument provided to the method.
        ResultActions performResult = mockMvc.perform(MockMvcRequestBuilders.get(endpoint, expectedCustomerDTO.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        performResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedCustomerDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value(expectedCustomerDTO.getCustomerName()));
    }

    @Test
    void givenCustomerService_whenFetchingCustomerList_thenCustomerListIsReturnedAsJson() throws Exception {
        // Arrange
        List<CustomerDTO> expectedCustomerDTOS = new ArrayList<>();
        expectedCustomerDTOS.add(CustomerDTO.builder().id(UUID.randomUUID()).customerName("Eragon Shadeslayer").build());
        expectedCustomerDTOS.add(CustomerDTO.builder().id(UUID.randomUUID()).customerName("Will Treaty").build());
        when(mockCustomerService.listCustomers()).thenReturn(expectedCustomerDTOS);
        String endpoint = "/api/v1/customer";

        // Act
        ResultActions performResult = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        performResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedCustomerDTOS.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(expectedCustomerDTOS.get(0).getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerName").value(expectedCustomerDTOS.get(0).getCustomerName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(expectedCustomerDTOS.get(1).getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].customerName").value(expectedCustomerDTOS.get(1).getCustomerName()));
    }

    @Test
    void testControllerCanHandleNotFoundException() throws Exception {
        BDDMockito.given(mockCustomerService.getCustomerById(BDDMockito.any()))
                .willThrow(NotFoundException.class);

        String endpoint = CustomerController.BASE_URL + "/" + UUID.randomUUID();
        var result = mockMvc.perform(MockMvcRequestBuilders.get(endpoint));

        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // TODO: skipped assignment of testing create customer
    // TODO: skipped assignment of testing update customer
    // TODO: skipped assignment of testing delete customer
    // TODO: skipped assignment of testing patch customer
}