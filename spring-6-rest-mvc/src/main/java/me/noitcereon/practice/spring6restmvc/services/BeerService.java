package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    Beer updateBeerById(UUID beerId, Beer updatedBeer);

    Optional<Beer> deleteBeerById(UUID id);
}
