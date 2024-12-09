package me.noitcereon.practice.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.noitcereon.practice.spring6restmvc.models.Beer;
import me.noitcereon.practice.spring6restmvc.services.BeerService;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@Slf4j
@AllArgsConstructor
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID id){
        log.info("Retrieving beer by id " + id);
        return beerService.getBeerById(id);
    }
}
