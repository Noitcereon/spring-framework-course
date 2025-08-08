package me.noitcereon.practice.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.noitcereon.practice.spring6restmvc.controllers.exception.NotFoundException;
import me.noitcereon.practice.spring6restmvc.models.BeerDTO;
import me.noitcereon.practice.spring6restmvc.services.BeerService;
import me.noitcereon.practice.spring6restmvc.services.BeerServiceMapDataImpl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;

@WebMvcTest(BeerController.class) // A Spring "Test Slice" to minimize test setup. See also:
// https://docs.spring.io/spring-boot/appendix/test-auto-configuration/slices.html
class BeerControllerTest {

  @Autowired MockMvc mockMvc;

  @MockitoBean BeerService mockBeerService;

  ObjectMapper objectMapper;

  private BeerServiceMapDataImpl beerServiceMapDataImpl;

  @BeforeEach
  void setUp() {
    beerServiceMapDataImpl = new BeerServiceMapDataImpl();

    /*
     * JavaTimeModule is a class that registers capability of serializing java.time objects
     * with the Jackson core.
     */
    objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
  }

  @Test
  void givenBeerService_whenCallingCreateBeerEndpoint_thenCreatedResponseIsReturned()
      throws Exception {
    BeerDTO newBeerDTO = beerServiceMapDataImpl.listBeers().get(0);
    newBeerDTO.setId(null);
    newBeerDTO.setVersion(null);

    BDDMockito.given(mockBeerService.saveNewBeer(ArgumentMatchers.any(BeerDTO.class)))
        .willReturn(beerServiceMapDataImpl.listBeers().get(1));

    String endpoint = BeerController.BASE_URL;
    var performResult =
        mockMvc.perform(
            MockMvcRequestBuilders.post(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBeerDTO)));

    performResult
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.header().exists("Location"));
  }

  @Test
  void testUpdateBeer() throws Exception {
    // Arrange
    BeerDTO beerDTOToUpdate = beerServiceMapDataImpl.listBeers().get(0);

    BDDMockito.given(
            mockBeerService.updateBeerById(
                ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BeerDTO.class)))
        .willReturn(Optional.of(beerDTOToUpdate));
    String endpoint = BeerController.BASE_URL + "/" + beerDTOToUpdate.getId();

    // Act
    ResultActions performResult =
        mockMvc.perform(
            MockMvcRequestBuilders.put(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDTOToUpdate)));

    // Assert
    performResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(beerDTOToUpdate.getId().toString())));
  }

  @Test
  void givenBeerService_whenFetchingBeerList_thenReturnOkResponseWithBeersInJson()
      throws Exception {
    // Arrange
    List<BeerDTO> testBeerDTOS = new ArrayList<>();
    testBeerDTOS.add(BeerDTO.builder().beerName("Test Beer").id(UUID.randomUUID()).build());
    testBeerDTOS.add(BeerDTO.builder().beerName("Beer Test").id(UUID.randomUUID()).build());
    String endpoint = "/api/v1/beer";
    BDDMockito.given(mockBeerService.listBeers()).willReturn(testBeerDTOS);

    // Act
    ResultActions performResult =
        mockMvc.perform(MockMvcRequestBuilders.get(endpoint).accept(MediaType.APPLICATION_JSON));

    // Assert
    performResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$[0].beerName", is(testBeerDTOS.get(0).getBeerName())))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$[0].id", is(testBeerDTOS.get(0).getId().toString())))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$[1].beerName", is(testBeerDTOS.get(1).getBeerName())))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$[1].id", is(testBeerDTOS.get(1).getId().toString())));
  }

  @Test
  void fetchingBeerByIdShouldReturnOkResponseWithBeerJson() throws Exception {
    // Arrange
    BeerDTO testBeerDTO = BeerDTO.builder().beerName("Test Beer").id(UUID.randomUUID()).build();
    String endpoint = "/api/v1/beer/" + testBeerDTO.getId();
    BDDMockito.given(mockBeerService.getBeerById(testBeerDTO.getId()))
        .willReturn(Optional.of(testBeerDTO));

    // Act
    ResultActions performResult =
        mockMvc.perform(MockMvcRequestBuilders.get(endpoint).accept(MediaType.APPLICATION_JSON));

    // Assert
    performResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.beerName", is(testBeerDTO.getBeerName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testBeerDTO.getId().toString())));
  }

  @Test
  void testDeleteBeerById() throws Exception {
    // Arrange
    BeerDTO simulatedExistingBeerDTO = beerServiceMapDataImpl.listBeers().get(0);
    String endpoint = BeerController.BASE_URL + "/" + simulatedExistingBeerDTO.getId();
    BDDMockito.given(mockBeerService.deleteBeerById(simulatedExistingBeerDTO.getId()))
        .willReturn(true);
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

    var result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(BeerController.BASE_URL + "/" + UUID.randomUUID()));

    result.andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  /** Tests the Java Bean Validation (Example annotations: @Validated and @NotNull, @NotBlank) */
  @Test
  void testCreateBeerNullBeerName() throws Exception {
    BeerDTO beerDTO = BeerDTO.builder().build();

    // Given
    BDDMockito.given(mockBeerService.saveNewBeer(BDDMockito.any(BeerDTO.class)))
        .willReturn(beerServiceMapDataImpl.listBeers().get(1));

    // When
    ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.post(BeerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDTO)));

    // Then
    MvcResult mvcResult =
        result.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    String responseContent = mvcResult.getResponse().getContentAsString();
    Assertions.assertFalse(responseContent.isBlank());
    Assertions.assertTrue(responseContent.contains("{beerName=must not be null}"));
    Assertions.assertTrue(responseContent.contains("{beerName=must not be blank}"));
  }

  @Test
  void updateBeerValidationWorks() throws Exception {
    // Given
    String beerId = UUID.randomUUID().toString();
    BeerDTO beerDto = BeerDTO.builder().build();
    String updateUrl = BeerController.BASE_URL + "/%s".formatted(beerId);
    String unexpectedBlank = "";
    // When
    ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.put(updateUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDto)));

    // Then
    MvcResult mvcResult =
        result.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    String errorResponse = mvcResult.getResponse().getContentAsString();

    Assertions.assertNotEquals(unexpectedBlank, errorResponse);
    Assertions.assertTrue(errorResponse.contains("{beerName=must not be blank}"));
    Assertions.assertTrue(errorResponse.contains("{beerName=must not be null}"));
    Assertions.assertTrue(errorResponse.contains("{beerStyle=must not be null}"));
    Assertions.assertTrue(errorResponse.contains("{upc=must not be null}"));
    Assertions.assertTrue(errorResponse.contains("{price=must not be null}"));
  }
}
