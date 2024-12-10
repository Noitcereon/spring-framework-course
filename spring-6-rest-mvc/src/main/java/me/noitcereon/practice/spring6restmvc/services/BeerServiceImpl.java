package me.noitcereon.practice.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import me.noitcereon.practice.spring6restmvc.models.Beer;
import me.noitcereon.practice.spring6restmvc.models.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.fromString("856477d4-ab9a-4d6f-a031-d44b8cb5c584"))
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123536")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }


    @Override
    public List<Beer> listBeers(){
        return new ArrayList<>(beerMap.values());
    }
    @Override
    public Beer getBeerById(UUID id) {
        return beerMap.get(id);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {
        Beer newBeer = Beer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .price(beer.getPrice())
                .upc(beer.getUpc())
                .version(beer.getVersion())
                .build();

        beerMap.put(newBeer.getId(), newBeer);
        return newBeer;
    }

    @Override
    public Beer updateBeerById(UUID beerId, Beer updatedBeer) {
        Beer beerToBeUpdated = beerMap.get(beerId);
        if(beerToBeUpdated == null){
            log.warn("Could not find beer with id '" + beerId + "' creating a new beer with that id.");
            beerToBeUpdated = Beer.builder().build();
            beerToBeUpdated.setCreatedDate(LocalDateTime.now());
        }
        beerToBeUpdated.setId(beerId);
        beerToBeUpdated.setVersion(updatedBeer.getVersion());
        beerToBeUpdated.setBeerName(updatedBeer.getBeerName());
        beerToBeUpdated.setBeerStyle(updatedBeer.getBeerStyle());
        beerToBeUpdated.setQuantityOnHand(updatedBeer.getQuantityOnHand());
        beerToBeUpdated.setPrice(updatedBeer.getPrice());
        beerToBeUpdated.setUpc(updatedBeer.getUpc());
        beerToBeUpdated.setUpdateDate(LocalDateTime.now());
        return beerMap.put(beerToBeUpdated.getId(), beerToBeUpdated);
    }

    @Override
    public Optional<Beer> deleteBeerById(UUID id) {
        Beer beer = beerMap.remove(id);
        return Optional.ofNullable(beer);
    }

    @Override
    public Optional<Beer> patchBeerById(UUID beerId, Beer updatedBeer) {
        Beer existingBeer = beerMap.get(beerId);
        if(existingBeer == null) return Optional.empty();
        boolean wasUpdated = false;
        if(updatedBeer.getBeerName() != null){
            existingBeer.setBeerName(updatedBeer.getBeerName());
            wasUpdated = true;
        }
        if(updatedBeer.getPrice() != null){
            existingBeer.setPrice(updatedBeer.getPrice());
            wasUpdated = true;
        }

        // I could do the same checks for the remaining beer properties, but I won't because it is not needed for learning...

        if(wasUpdated){
            existingBeer.setUpdateDate(LocalDateTime.now());
        }
        return Optional.of(existingBeer);
    }
}
