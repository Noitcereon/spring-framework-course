package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO updatedBeerDTO);

    Optional<BeerDTO> deleteBeerById(UUID id);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO updatedBeerDTO);
}
