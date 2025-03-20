package me.noitcereon.practice.spring6restmvc.bootstrap;

import lombok.RequiredArgsConstructor;
import me.noitcereon.practice.spring6restmvc.entities.Beer;
import me.noitcereon.practice.spring6restmvc.entities.Customer;
import me.noitcereon.practice.spring6restmvc.models.BeerStyle;
import me.noitcereon.practice.spring6restmvc.repositories.BeerRepo;
import me.noitcereon.practice.spring6restmvc.repositories.CustomerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Initializes the database with a baseline of data, if nothing is present.
 */
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final BeerRepo beerRepo;
    private final CustomerRepo customerRepo;

    @Override
    public void run(String... args) {
        if (beerRepo.count() == 0) {
            seedBeerData();
        }
        if (customerRepo.count() == 0) {
            seedCustomerData();
        }
    }

    private void seedBeerData() {
        Beer beer1 = Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123536")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now(ZoneOffset.UTC))
                .updateDate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now(ZoneOffset.UTC))
                .updateDate(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        Beer beer3 = Beer.builder()
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now(ZoneOffset.UTC))
                .updateDate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        List<Beer> beers = List.of(beer1, beer2, beer3);
        beerRepo.saveAll(beers);
    }

    private void seedCustomerData() {
        Customer customer1 = Customer.builder()
                .customerName("Noit Cereon")
                .createdDate(LocalDateTime.now(ZoneOffset.UTC))
                .lastModifiedDate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        Customer customer2 = Customer.builder()
                .customerName("Eragon Shadeslayer")
                .createdDate(LocalDateTime.now(ZoneOffset.UTC))
                .lastModifiedDate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        Customer customer3 = Customer.builder()
                .customerName("Will Treaty")
                .createdDate(LocalDateTime.now(ZoneOffset.UTC))
                .lastModifiedDate(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        List<Customer> customers = List.of(customer1, customer2, customer3);
        customerRepo.saveAll(customers);
    }
}
