package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.CustomerDTO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceMapDataImpl implements CustomerService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CustomerServiceMapDataImpl.class);
    private final Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceMapDataImpl() {
        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Noit Cereon")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .id(UUID.fromString("eae5e6d4-d511-4200-b5d7-73c485701c41"))
                .customerName("Eragon Shadeslayer")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        CustomerDTO customerDTO3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Will Treaty")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        customerMap = new HashMap<>();
        customerMap.put(customerDTO1.getId(), customerDTO1);
        customerMap.put(customerDTO2.getId(), customerDTO2);
        customerMap.put(customerDTO3.getId(), customerDTO3);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMap.get(id));
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO newCustomerDTO) {
        LocalDateTime now = LocalDateTime.now();
        CustomerDTO savedCustomerDTO = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(newCustomerDTO.getVersion())
                .customerName(newCustomerDTO.getCustomerName())
                .createdDate(now)
                .lastModifiedDate(now)
                .build();
        customerMap.put(savedCustomerDTO.getId(), savedCustomerDTO);
        return savedCustomerDTO;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO updatedCustomerDTO) {
        CustomerDTO customerDTOToBeUpdated = customerMap.get(customerId);
        if (customerDTOToBeUpdated == null) {
            log.warn("Could not find customer with id '{}'", customerId);
            return Optional.empty();
        }
        customerDTOToBeUpdated.setCustomerName(updatedCustomerDTO.getCustomerName());
        customerDTOToBeUpdated.setLastModifiedDate(LocalDateTime.now());
        return Optional.of(customerDTOToBeUpdated);
    }

    @Override
    public Optional<CustomerDTO> deleteCustomerId(UUID id) {
        CustomerDTO existingCustomerDTO = customerMap.remove(id);
        return Optional.ofNullable(existingCustomerDTO);
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO updatedCustomerDTO) {
        CustomerDTO existingCustomerDTO = customerMap.get(customerId);
        if (existingCustomerDTO == null) return Optional.empty();
        boolean wasUpdated = false;

        if (StringUtils.hasText(updatedCustomerDTO.getCustomerName())) {
            existingCustomerDTO.setCustomerName(updatedCustomerDTO.getCustomerName());
            wasUpdated = true;
        }
        if (wasUpdated) {
            existingCustomerDTO.setLastModifiedDate(LocalDateTime.now());
        }
        return Optional.of(existingCustomerDTO);
    }
}
