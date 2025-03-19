package me.noitcereon.practice.spring6restmvc.controllers;

import jakarta.transaction.Transactional;
import me.noitcereon.practice.spring6restmvc.models.BeerDTO;
import me.noitcereon.practice.spring6restmvc.repositories.BeerRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class BeerControllerIntegrationTest {

    @Autowired
    private BeerController beerController;

    @Autowired
    private BeerRepo beerRepo;

    @Test
    void testGetAllBeersSuccess(){
        List<BeerDTO> beerDtos = beerController.getBeerList();

        assertEquals(3, beerDtos.size());
    }

    @Transactional
    @Rollback
    @Test
    void givenNoBeerEntitiesInDatabase_WhenFetchingAllBeers_Then0BeersAreReturned(){
        beerRepo.deleteAll();
        List<BeerDTO> allBeers = beerController.getBeerList();

        assertEquals(0, allBeers.size());
    }

    @Test
    void givenUnknownId_WhenFetchingBeerById_ThenEmptyOptionalIsReturned(){
        UUID randomId = UUID.randomUUID();
        Optional<BeerDTO> beerDto = beerController.getBeerById(randomId);
        assertEquals(Optional.empty(), beerDto);
    }

    @Test
    void givenExistingBeerId_WhenFetchingBeerById_ThenBeerIsFound(){
        UUID beerId = beerRepo.findAll().getFirst().getId();

        Optional<BeerDTO> beerDto = beerController.getBeerById(beerId);

        assertEquals(beerId, beerDto.orElseThrow().getId());
    }
}