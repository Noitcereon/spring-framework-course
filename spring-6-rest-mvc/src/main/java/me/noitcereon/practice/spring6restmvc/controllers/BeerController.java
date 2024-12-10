package me.noitcereon.practice.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.noitcereon.practice.spring6restmvc.models.Beer;
import me.noitcereon.practice.spring6restmvc.services.BeerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @PostMapping
    public ResponseEntity createBeer(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);
       return ResponseEntity
                .created(URI.create("/api/v1/beer/" + savedBeer.getId()))
                .build();
    }

    @GetMapping
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }
    @GetMapping("/{id}")
    public Beer getBeerById(@PathVariable UUID id) {
        log.info("Retrieving beer by id: " + id);
        return beerService.getBeerById(id);
    }
}
