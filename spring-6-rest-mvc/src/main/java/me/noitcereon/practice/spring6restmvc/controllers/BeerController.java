package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.models.Beer;
import me.noitcereon.practice.spring6restmvc.services.BeerService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BeerController.class);
    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping
    public List<Beer> getBeerList() {
        return beerService.listBeers();
    }

    @GetMapping("/{beerId}")
    public Beer getBeerById(@PathVariable UUID beerId) {
        log.info("Retrieving beer by id " + beerId);
        return beerService.getBeerById(beerId);
    }

    @PostMapping
    public ResponseEntity<Beer> createBeer(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);
        return ResponseEntity
                .created(URI.create("/api/v1/beer/" + savedBeer.getId()))
                .build();
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<Beer> updateBeerById(@PathVariable UUID beerId, @RequestBody Beer updatedBeer) {
        Beer savedBeer = beerService.updateBeerById(beerId, updatedBeer);
        return ResponseEntity.ok().body(savedBeer);
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID id) {
        Optional<Beer> deletedBeer = beerService.deleteBeerById(id);
        if (deletedBeer.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{beerId}")
    public ResponseEntity<Beer> patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody Beer updatedBeer) {
        Optional<Beer> savedBeer = beerService.patchBeerById(beerId, updatedBeer);
        if (savedBeer.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(savedBeer.get());
    }
}
