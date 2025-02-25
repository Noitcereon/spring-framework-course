package me.noitcereon.practice.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.noitcereon.practice.spring6restmvc.models.Beer;
import me.noitcereon.practice.spring6restmvc.services.BeerService;
import me.noitcereon.practice.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
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
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;

@WebMvcTest(BeerController.class) // A Spring "Test Slice" to minimize test setup. See also: https://docs.spring.io/spring-boot/appendix/test-auto-configuration/slices.html
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BeerService mockBeerService;

    ObjectMapper objectMapper;

    private BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();

        /*
         * JavaTimeModule is a class that registers capability of serializing java.time objects
         * with the Jackson core.
         */
        objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
    }

    @Test
    void givenBeerService_whenCallingCreateBeerEndpoint_thenCreatedResponseIsReturned() throws Exception {
        Beer newBeer = beerServiceImpl.listBeers().get(0);
        newBeer.setId(null);
        newBeer.setVersion(null);

        BDDMockito.given(mockBeerService.saveNewBeer(ArgumentMatchers.any(Beer.class)))
                .willReturn(beerServiceImpl.listBeers().get(1));

        String endpoint = BeerController.BASE_URL;
        var performResult = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBeer)));

        performResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }

    @Test
    void testUpdateBeer() throws Exception {
        // Arrange
        Beer beerToUpdate = beerServiceImpl.listBeers().get(0);

        BDDMockito.given(mockBeerService.updateBeerById(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(Beer.class)))
                .willReturn(beerToUpdate);
        String endpoint = BeerController.BASE_URL + "/" + beerToUpdate.getId();

        // Act
        ResultActions performResult = mockMvc.perform(MockMvcRequestBuilders.put(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerToUpdate)));

        // Assert
        performResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(beerToUpdate.getId().toString())));
    }

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

    @Test
    void testDeleteBeerById() throws Exception {
        // Arrange
        Beer simulatedExistingBeer = beerServiceImpl.listBeers().get(0);
        String endpoint = BeerController.BASE_URL + "/" + simulatedExistingBeer.getId();
        Optional<Beer> stubResponse = Optional.of(simulatedExistingBeer);
        BDDMockito.given(mockBeerService.deleteBeerById(simulatedExistingBeer.getId()))
                .willReturn(stubResponse);
        ArgumentCaptor<UUID> argCaptor = ArgumentCaptor.forClass(UUID.class);

        // Act
        var performResult = mockMvc.perform(MockMvcRequestBuilders.delete(endpoint));

        // Assert
        /*
         * Verifies the mock was called and captures the input given to the mocked call.
         * in the argCaptor object.
         */
        BDDMockito.verify(mockBeerService).deleteBeerById(argCaptor.capture());
        Assertions.assertEquals(simulatedExistingBeer.getId(), argCaptor.getValue());

        performResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}