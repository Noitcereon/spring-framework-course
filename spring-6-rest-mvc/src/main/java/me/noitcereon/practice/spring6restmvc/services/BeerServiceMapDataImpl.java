package me.noitcereon.practice.spring6restmvc.services;

import me.noitcereon.practice.spring6restmvc.models.BeerDTO;
import me.noitcereon.practice.spring6restmvc.models.BeerStyle;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BeerServiceMapDataImpl implements BeerService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BeerServiceMapDataImpl.class);
    private final Map<UUID, BeerDTO> beerMap;

    public BeerServiceMapDataImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beerDTO1 = BeerDTO.builder()
                .id(UUID.fromString("856477d4-ab9a-4d6f-a031-d44b8cb5c584"))
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123536")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beerDTO2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beerDTO3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beerDTO1.getId(), beerDTO1);
        beerMap.put(beerDTO2.getId(), beerDTO2);
        beerMap.put(beerDTO3.getId(), beerDTO3);
    }


    @Override
    public List<BeerDTO> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        BeerDTO newBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .price(beerDTO.getPrice())
                .upc(beerDTO.getUpc())
                .version(beerDTO.getVersion())
                .build();

        beerMap.put(newBeerDTO.getId(), newBeerDTO);
        return newBeerDTO;
    }

    @Override
    public BeerDTO updateBeerById(UUID beerId, BeerDTO updatedBeerDTO) {
        BeerDTO beerDTOToBeUpdated = beerMap.get(beerId);
        if (beerDTOToBeUpdated == null) {
            log.warn("Could not find beer with id '{}' creating a new beer with that id.", beerId);
            beerDTOToBeUpdated = BeerDTO.builder().build();
            beerDTOToBeUpdated.setCreatedDate(LocalDateTime.now());
        }
        beerDTOToBeUpdated.setId(beerId);
        beerDTOToBeUpdated.setVersion(updatedBeerDTO.getVersion());
        beerDTOToBeUpdated.setBeerName(updatedBeerDTO.getBeerName());
        beerDTOToBeUpdated.setBeerStyle(updatedBeerDTO.getBeerStyle());
        beerDTOToBeUpdated.setQuantityOnHand(updatedBeerDTO.getQuantityOnHand());
        beerDTOToBeUpdated.setPrice(updatedBeerDTO.getPrice());
        beerDTOToBeUpdated.setUpc(updatedBeerDTO.getUpc());
        beerDTOToBeUpdated.setUpdateDate(LocalDateTime.now());
        return beerMap.put(beerDTOToBeUpdated.getId(), beerDTOToBeUpdated);
    }

    @Override
    public Optional<BeerDTO> deleteBeerById(UUID id) {
        BeerDTO beerDTO = beerMap.remove(id);
        return Optional.ofNullable(beerDTO);
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO updatedBeerDTO) {
        BeerDTO existingBeerDTO = beerMap.get(beerId);
        if (existingBeerDTO == null) return Optional.empty();
        boolean wasUpdated = false;
        if (updatedBeerDTO.getBeerName() != null) {
            existingBeerDTO.setBeerName(updatedBeerDTO.getBeerName());
            wasUpdated = true;
        }
        if (updatedBeerDTO.getPrice() != null) {
            existingBeerDTO.setPrice(updatedBeerDTO.getPrice());
            wasUpdated = true;
        }

        // I could do the same checks for the remaining beer properties, but I won't because it is not needed for learning...

        if (wasUpdated) {
            existingBeerDTO.setUpdateDate(LocalDateTime.now());
        }
        return Optional.of(existingBeerDTO);
    }
}
