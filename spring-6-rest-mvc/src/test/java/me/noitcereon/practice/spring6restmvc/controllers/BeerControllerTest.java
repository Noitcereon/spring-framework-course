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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;

@WebMvcTest(BeerController.class) // A Spring "Test Slice" to minimize test setup. See also: https://docs.spring.io/spring-boot/appendix/test-auto-configuration/slices.html
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BeerService mockBeerService;

    @Test
    void givenBeerService_whenFetchingBeerList_thenReturnOkResponseWithBeersInJson() throws Exception {
        // Arrange
        List<Beer> testBeers = new ArrayList<>();
        testBeers.add(Beer.builder().beerName("Test Beer").id(UUID.randomUUID()).build());
        testBeers.add(Beer.builder().beerName("Beer Test").id(UUID.randomUUID()).build());
        String endpoint = "/api/v1/beer";
        BDDMockito.given(mockBeerService.listBeers()).willReturn(testBeers);

        // Act
        ResultActions performResult = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        performResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].beerName", is(testBeers.get(0).getBeerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(testBeers.get(0).getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].beerName", is(testBeers.get(1).getBeerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(testBeers.get(1).getId().toString())));
    }

    @Test
    void fetchingBeerByIdShouldReturnOkResponseWithBeerJson() throws Exception {
        // Arrange
        Beer testBeer = Beer.builder().beerName("Test Beer").id(UUID.randomUUID()).build();
        String endpoint = "/api/v1/beer/" + testBeer.getId();
        BDDMockito.given(mockBeerService.getBeerById(testBeer.getId()))
                .willReturn(testBeer);

        // Act
        ResultActions performResult = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        performResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.beerName", is(testBeer.getBeerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testBeer.getId().toString())));
    }
}