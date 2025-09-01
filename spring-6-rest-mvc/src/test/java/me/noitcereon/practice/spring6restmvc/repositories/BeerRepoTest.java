package me.noitcereon.practice.spring6restmvc.repositories;

import me.noitcereon.practice.spring6restmvc.entities.Beer;
import me.noitcereon.practice.spring6restmvc.models.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

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
    Beer savedBeer =
        beerRepo.save(
            Beer.builder()
                .beerName(expectedBeerName)
                .beerStyle(BeerStyle.IPA)
                .upc("something")
                .price(new BigDecimal("59.99"))
                .build());

        /* Flush to trigger validation */
        beerRepo.flush();

        assertNotNull(savedBeer.getId());
        assertEquals(expectedBeerName, savedBeer.getBeerName());
    }
}