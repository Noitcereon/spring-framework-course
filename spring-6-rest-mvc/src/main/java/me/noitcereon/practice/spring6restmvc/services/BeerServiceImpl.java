package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.Beer;
import me.noitcereon.practice.spring6restmvc.models.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeerById(UUID id) {
        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("Galaxy Cat 2")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(BigDecimal.valueOf(14.95))
                .quantityOnHand(142)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
