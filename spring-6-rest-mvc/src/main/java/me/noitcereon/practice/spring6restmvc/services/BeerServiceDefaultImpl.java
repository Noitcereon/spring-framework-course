package me.noitcereon.practice.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import me.noitcereon.practice.spring6restmvc.entities.Beer;
import me.noitcereon.practice.spring6restmvc.mappers.BeerMapper;
import me.noitcereon.practice.spring6restmvc.models.BeerDTO;
import me.noitcereon.practice.spring6restmvc.repositories.BeerRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@RequiredArgsConstructor
@Service
public class BeerServiceDefaultImpl implements BeerService {

    private final BeerRepo beerRepo;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        List<Beer> beers = beerRepo.findAll();
        return beerMapper.beersToBeerDtos(beers);
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(
                beerRepo.findById(id).orElse(null)
        ));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return null;
    }

    @Override
    public BeerDTO updateBeerById(UUID beerId, BeerDTO updatedBeerDTO) {
        return null;
    }

    @Override
    public Optional<BeerDTO> deleteBeerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO updatedBeerDTO) {
        return Optional.empty();
    }
}
