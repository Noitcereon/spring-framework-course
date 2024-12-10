package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.Beer;
import me.noitcereon.practice.spring6restmvc.models.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat 2")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(BigDecimal.valueOf(14.95))
                .quantityOnHand(142)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Classic")
                .beerStyle(BeerStyle.PILSNER)
                .upc("12356")
                .price(BigDecimal.valueOf(5.95))
                .quantityOnHand(142)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();


        this.beerMap = new HashMap<>();
        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
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
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }
}
