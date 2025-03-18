package me.noitcereon.practice.spring6restmvc.repositories;

import me.noitcereon.practice.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class CustomerRepoTest {

    @Autowired
    private CustomerRepo customerRepo;

    @Test
    void saveCustomer() {
        String expectedName = "Eragon";
        Customer savedCustomer = customerRepo.save(Customer.builder()
                .customerName(expectedName)
                .build());
        assertNotNull(savedCustomer.getId());
        assertEquals(expectedName, savedCustomer.getCustomerName());
    }
}