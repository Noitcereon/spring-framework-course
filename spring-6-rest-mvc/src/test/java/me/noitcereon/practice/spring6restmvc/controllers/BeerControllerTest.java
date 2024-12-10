package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.models.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerTest {

    @Autowired
    BeerController controller;

    @Test
    void givenAnIdForSpecificBeer_WhenFetchingBeerById_ThenReturnThatBeer() {
        String expectedBeerName = "Galaxy Cat";
        Beer actual = controller.getBeerById(UUID.fromString("856477d4-ab9a-4d6f-a031-d44b8cb5c584"));

        assertEquals(expectedBeerName, actual.getBeerName());
    }
}