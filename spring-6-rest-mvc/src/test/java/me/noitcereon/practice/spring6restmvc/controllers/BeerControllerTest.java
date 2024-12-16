package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.models.Beer;
import me.noitcereon.practice.spring6restmvc.services.BeerService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(BeerController.class) // A Spring "Test Slice" to minimize test setup. See also: https://docs.spring.io/spring-boot/appendix/test-auto-configuration/slices.html
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BeerService mockBeerService;

    @Test
    void fetchingBeerByIdShouldReturnOkResponse() throws Exception{
        Beer testBeer = Beer.builder().beerName("Test Beer").build();
        String endpoint = "/api/v1/beer/" + UUID.randomUUID();
        BDDMockito.given(mockBeerService.getBeerById(any(UUID.class)))
                .willReturn(testBeer);

        mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}