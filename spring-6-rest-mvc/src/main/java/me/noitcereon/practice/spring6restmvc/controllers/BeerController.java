package me.noitcereon.practice.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.noitcereon.practice.spring6restmvc.models.Beer;
import me.noitcereon.practice.spring6restmvc.services.BeerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @GetMapping
    public List<Beer> getBeerList(){
        return beerService.listBeers();
    }

    @GetMapping("/{beerId}")
    public Beer getBeerById(@PathVariable UUID beerId){
        log.info("Retrieving beer by id " + beerId);
        return beerService.getBeerById(beerId);
    }
}
