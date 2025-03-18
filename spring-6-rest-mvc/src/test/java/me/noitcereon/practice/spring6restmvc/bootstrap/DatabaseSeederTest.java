package me.noitcereon.practice.spring6restmvc.bootstrap;

import me.noitcereon.practice.spring6restmvc.repositories.BeerRepo;
import me.noitcereon.practice.spring6restmvc.repositories.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DatabaseSeederTest {

    @Autowired
    private BeerRepo beerRepo;

    @Autowired
    private CustomerRepo customerRepo;

    private DatabaseSeeder databaseSeeder;

    @BeforeEach
    void setup() {
        databaseSeeder = new DatabaseSeeder(beerRepo, customerRepo);
    }

    @Test
    void seedDatabaseSuccess() {
        assertEquals(0, beerRepo.count());
        assertEquals(0, beerRepo.count());

        databaseSeeder.run();

        assertEquals(3, beerRepo.count());
        assertEquals(3, customerRepo.count());
    }
}