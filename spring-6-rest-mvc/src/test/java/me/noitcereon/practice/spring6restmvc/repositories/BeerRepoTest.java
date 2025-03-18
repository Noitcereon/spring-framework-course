package me.noitcereon.practice.spring6restmvc.repositories;

import me.noitcereon.practice.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class BeerRepoTest {

    @Autowired
    BeerRepo beerRepo;

    @Test
    void testSaveBeer() {
        String expectedBeerName = "Holy Smokes";
        Beer savedBeer = beerRepo.save(Beer.builder()
                .beerName(expectedBeerName)
                .build());

        assertNotNull(savedBeer.getId());
        assertEquals(expectedBeerName, savedBeer.getBeerName());
    }
}