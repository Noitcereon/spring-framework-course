package me.noitcereon.practice.spring6restmvc.mappers;

import me.noitcereon.practice.spring6restmvc.entities.Customer;
import me.noitcereon.practice.spring6restmvc.models.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
