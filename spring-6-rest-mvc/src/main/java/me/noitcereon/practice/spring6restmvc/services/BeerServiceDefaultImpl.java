package me.noitcereon.practice.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import me.noitcereon.practice.spring6restmvc.entities.Beer;
import me.noitcereon.practice.spring6restmvc.mappers.BeerMapper;
import me.noitcereon.practice.spring6restmvc.models.BeerDTO;
import me.noitcereon.practice.spring6restmvc.repositories.BeerRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

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
    public BeerDTO saveNewBeer(BeerDTO beerDto) {
        beerDto.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
        Beer savedBeer = beerRepo.save(beerMapper.beerDtoToBeer(beerDto));
        return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO updatedBeerDto) {

        AtomicReference<Optional<BeerDTO>> atomicBeerDtoReference = new AtomicReference<>();

        beerRepo.findById(beerId).ifPresentOrElse(foundBeer -> {
                    /* Note: ID and Version is managed by Hibernate and should not be changed via dto object */
                    foundBeer.setBeerName(updatedBeerDto.getBeerName());
                    foundBeer.setBeerStyle(updatedBeerDto.getBeerStyle());
                    foundBeer.setUpc(updatedBeerDto.getUpc());
                    foundBeer.setPrice(updatedBeerDto.getPrice());
                    foundBeer.setQuantityOnHand(updatedBeerDto.getQuantityOnHand());
                    foundBeer.setUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
                    atomicBeerDtoReference.set(Optional.of(beerMapper.beerToBeerDto(foundBeer)));
                }, () -> atomicBeerDtoReference.set(Optional.empty())
        );

        return atomicBeerDtoReference.get();
    }

    @Override
    public Boolean deleteBeerById(UUID id) {
        boolean didDelete = false;
        if(beerRepo.existsById(id)) {
            beerRepo.deleteById(id);
            didDelete = true;
        }
        return didDelete;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO updatedBeerDTO) {
        return Optional.empty();
    }
}
