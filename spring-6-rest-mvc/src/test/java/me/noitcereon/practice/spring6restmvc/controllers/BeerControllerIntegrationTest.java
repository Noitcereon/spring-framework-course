package me.noitcereon.practice.spring6restmvc.controllers;

import jakarta.transaction.Transactional;
import me.noitcereon.practice.spring6restmvc.entities.Beer;
import me.noitcereon.practice.spring6restmvc.mappers.BeerMapper;
import me.noitcereon.practice.spring6restmvc.models.BeerDTO;
import me.noitcereon.practice.spring6restmvc.repositories.BeerRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class BeerControllerIntegrationTest {

    @Autowired
    private BeerController beerController;

    @Autowired
    private BeerRepo beerRepo;

    @Autowired
    private BeerMapper beerMapper;

    @Test
    void testGetAllBeersSuccess() {
        List<BeerDTO> beerDtos = beerController.getBeerList();

        assertEquals(3, beerDtos.size());
    }

    @Transactional
    @Rollback
    @Test
    void givenNoBeerEntitiesInDatabase_WhenFetchingAllBeers_Then0BeersAreReturned() {
        beerRepo.deleteAll();
        List<BeerDTO> allBeers = beerController.getBeerList();

        assertEquals(0, allBeers.size());
    }

    @Test
    void givenUnknownId_WhenFetchingBeerById_ThenEmptyOptionalIsReturned() {
        UUID randomId = UUID.randomUUID();
        Optional<BeerDTO> beerDto = beerController.getBeerById(randomId);
        assertEquals(Optional.empty(), beerDto);
    }

    @Test
    void givenExistingBeerId_WhenFetchingBeerById_ThenBeerIsFound() {
        UUID beerId = beerRepo.findAll().getFirst().getId();

        Optional<BeerDTO> beerDto = beerController.getBeerById(beerId);

        assertEquals(beerId, beerDto.orElseThrow().getId());
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewBeerSuccess() {
        BeerDTO beerDtoToCreate = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity<BeerDTO> response = beerController.createBeer(beerDtoToCreate);
        URI createdLocation = response.getHeaders().getLocation();
        BeerDTO createdBeerDto = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(createdBeerDto);
        assertNotNull(createdLocation);
        assertEquals(URI.create("/api/v1/beer/" + createdBeerDto.getId()), createdLocation);
        Optional<Beer> beerEntity = beerRepo.findById(createdBeerDto.getId());
        assertTrue(beerEntity.isPresent());
        assertEquals(beerDtoToCreate.getBeerName(), beerEntity.get().getBeerName());
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewBeer_returnsBadRequestOnTooLongBeerName() {
        /* TODO: this should give a bad requeust */
        BeerDTO beerDtoToCreate = BeerDTO.builder()
                .beerName("New Beer1234567890123456789012345678901234567890 1234567890")
                .build();

        ResponseEntity<BeerDTO> response = beerController.createBeer(beerDtoToCreate);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateBeerSuccess() {
        // Arrange
        BeerDTO beerToChange = beerMapper.beerToBeerDto(beerRepo.findAll().getFirst());
        String updatedBeerName = "UPDATED " + beerToChange.getBeerName();
        beerToChange.setBeerName(updatedBeerName);

        // Act
        ResponseEntity<BeerDTO> response = beerController.updateBeerById(beerToChange.getId(), beerToChange);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        BeerDTO responseBeerDto = response.getBody();
        assertEquals(beerToChange.getId(), responseBeerDto.getId());
        assertEquals(updatedBeerName, responseBeerDto.getBeerName());
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteBeerByIdSuccess() {
        Beer beer = beerRepo.findAll().get(0);

        ResponseEntity<Void> deleteResponse1 = beerController.deleteBeerById(beer.getId());
        ResponseEntity<Void> deleteResponse2 = beerController.deleteBeerById(beer.getId());

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse1.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse2.getStatusCode());
    }

    @Rollback
    @Transactional
    @Test
    void testPatchBeerSuccess() {
        Beer existingBeer = beerRepo.findAll().get(0);
        BeerDTO existingBeerPatched = beerMapper.beerToBeerDto(existingBeer);
        existingBeerPatched.setBeerName("Patched Beer");

        ResponseEntity<BeerDTO> patchResponse = beerController.patchBeerById(existingBeer.getId(), existingBeerPatched);
        Optional<BeerDTO> getByIdResponse = beerController.getBeerById(existingBeer.getId());

        assertNotNull(patchResponse.getBody());
        assertEquals(HttpStatus.OK, patchResponse.getStatusCode());
        assertTrue(getByIdResponse.isPresent());
        assertEquals(existingBeerPatched.getBeerName(), patchResponse.getBody().getBeerName());
        assertEquals(existingBeerPatched.getId(), patchResponse.getBody().getId());
        assertEquals(existingBeer.getPrice(), patchResponse.getBody().getPrice());
        assertEquals(existingBeerPatched.getBeerName(), getByIdResponse.get().getBeerName());
    }
}