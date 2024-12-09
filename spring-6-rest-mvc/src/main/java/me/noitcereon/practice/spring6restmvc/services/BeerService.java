package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);
}
