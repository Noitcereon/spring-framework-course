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
    void givenAnId_WhenFetchingBeerById_ThenReturnGalaxyCat2Beer() {
        String expectedBeerName = "Galaxy Cat 2";
        Beer actual = controller.getBeerById(UUID.randomUUID());

        assertEquals(expectedBeerName, actual.getBeerName());
    }
}