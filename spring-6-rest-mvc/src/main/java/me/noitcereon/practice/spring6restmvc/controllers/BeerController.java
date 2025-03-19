package me.noitcereon.practice.spring6restmvc.controllers;

import me.noitcereon.practice.spring6restmvc.models.BeerDTO;
import me.noitcereon.practice.spring6restmvc.services.BeerService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(BeerController.BASE_URL)
public class BeerController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BeerController.class);
    private final BeerService beerService;
    public static final String BASE_URL = "/api/v1/beer";

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping
    public List<BeerDTO> getBeerList() {
        return beerService.listBeers();
    }

    @GetMapping("/{beerId}")
    public Optional<BeerDTO> getBeerById(@PathVariable UUID beerId) {
        log.info("Retrieving beer by id {}", beerId);
        return beerService.getBeerById(beerId);
    }

    @PostMapping
    public ResponseEntity<BeerDTO> createBeer(@RequestBody BeerDTO beerDTO) {
        BeerDTO savedBeerDTO = beerService.saveNewBeer(beerDTO);
        return ResponseEntity
                .created(URI.create("/api/v1/beer/" + savedBeerDTO.getId()))
                .body(savedBeerDTO);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDTO> updateBeerById(@PathVariable UUID beerId, @RequestBody BeerDTO updatedBeerDTO) {
        Optional<BeerDTO> savedBeerDTO = beerService.updateBeerById(beerId, updatedBeerDTO);
        return savedBeerDTO.map(beerDTO -> ResponseEntity.ok().body(beerDTO))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity<Void> deleteBeerById(@PathVariable("beerId") UUID id) {
        Optional<BeerDTO> deletedBeer = beerService.deleteBeerById(id);
        if (deletedBeer.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{beerId}")
    public ResponseEntity<BeerDTO> patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO updatedBeerDTO) {
        Optional<BeerDTO> savedBeer = beerService.patchBeerById(beerId, updatedBeerDTO);
        if (savedBeer.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(savedBeer.get());
    }
}
