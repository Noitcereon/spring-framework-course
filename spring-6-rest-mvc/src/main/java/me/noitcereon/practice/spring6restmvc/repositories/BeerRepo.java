package me.noitcereon.practice.spring6restmvc.repositories;

import me.noitcereon.practice.spring6restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepo extends JpaRepository<Beer, UUID> {
}
