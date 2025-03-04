package me.noitcereon.practice.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.noitcereon.practice.spring6restmvc.controllers.exception.NotFoundException;
import me.noitcereon.practice.spring6restmvc.models.BeerDTO;
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
        BeerDTO newBeerDTO = beerServiceImpl.listBeers().get(0);
        newBeerDTO.setId(null);
        newBeerDTO.setVersion(null);

        BDDMockito.given(mockBeerService.saveNewBeer(ArgumentMatchers.any(BeerDTO.class)))
                .willReturn(beerServiceImpl.listBeers().get(1));

        String endpoint = BeerController.BASE_URL;
        var performResult = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBeerDTO)));

        performResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }

    @Test
    void testUpdateBeer() throws Exception {
        // Arrange
        BeerDTO beerDTOToUpdate = beerServiceImpl.listBeers().get(0);

        BDDMockito.given(mockBeerService.updateBeerById(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BeerDTO.class)))
                .willReturn(beerDTOToUpdate);
        String endpoint = BeerController.BASE_URL + "/" + beerDTOToUpdate.getId();

        // Act
        ResultActions performResult = mockMvc.perform(MockMvcRequestBuilders.put(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDTOToUpdate)));

        // Assert
        performResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(beerDTOToUpdate.getId().toString())));
    }

    @Test
    void givenBeerService_whenFetchingBeerList_thenReturnOkResponseWithBeersInJson() throws Exception {
        // Arrange
        List<BeerDTO> testBeerDTOS = new ArrayList<>();
        testBeerDTOS.add(BeerDTO.builder().beerName("Test Beer").id(UUID.randomUUID()).build());
        testBeerDTOS.add(BeerDTO.builder().beerName("Beer Test").id(UUID.randomUUID()).build());
        String endpoint = "/api/v1/beer";
        BDDMockito.given(mockBeerService.listBeers()).willReturn(testBeerDTOS);

        // Act
        ResultActions performResult = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        performResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].beerName", is(testBeerDTOS.get(0).getBeerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(testBeerDTOS.get(0).getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].beerName", is(testBeerDTOS.get(1).getBeerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(testBeerDTOS.get(1).getId().toString())));
    }

    @Test
    void fetchingBeerByIdShouldReturnOkResponseWithBeerJson() throws Exception {
        // Arrange
        BeerDTO testBeerDTO = BeerDTO.builder().beerName("Test Beer").id(UUID.randomUUID()).build();
        String endpoint = "/api/v1/beer/" + testBeerDTO.getId();
        BDDMockito.given(mockBeerService.getBeerById(testBeerDTO.getId()))
                .willReturn(testBeerDTO);

        // Act
        ResultActions performResult = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        performResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.beerName", is(testBeerDTO.getBeerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testBeerDTO.getId().toString())));
    }

    @Test
    void testDeleteBeerById() throws Exception {
        // Arrange
        BeerDTO simulatedExistingBeerDTO = beerServiceImpl.listBeers().get(0);
        String endpoint = BeerController.BASE_URL + "/" + simulatedExistingBeerDTO.getId();
        Optional<BeerDTO> stubResponse = Optional.of(simulatedExistingBeerDTO);
        BDDMockito.given(mockBeerService.deleteBeerById(simulatedExistingBeerDTO.getId()))
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
        Assertions.assertEquals(simulatedExistingBeerDTO.getId(), argCaptor.getValue());

        performResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testFetchByIdNotFoundException() throws Exception {
        BDDMockito.given(mockBeerService.getBeerById(BDDMockito.any(UUID.class)))
                .willThrow(NotFoundException.class);

        var result = mockMvc.perform(MockMvcRequestBuilders.get(
                BeerController.BASE_URL + "/" + UUID.randomUUID()));

        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}