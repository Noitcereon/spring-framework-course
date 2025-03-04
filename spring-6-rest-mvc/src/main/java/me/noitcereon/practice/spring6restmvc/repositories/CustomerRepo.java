package me.noitcereon.practice.spring6restmvc.repositories;

import me.noitcereon.practice.spring6restmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepo extends JpaRepository<Customer, UUID> {
}
