package me.noitcereon.practice.spring6restmvc.mappers;

import me.noitcereon.practice.spring6restmvc.entities.Beer;
import me.noitcereon.practice.spring6restmvc.models.BeerDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO customerDto);

    BeerDTO beerToBeerDto(Beer customer);

    default List<BeerDTO> beersToBeerDtos(List<Beer> beers) {
        return beers.stream().map(this::beerToBeerDto).toList();
    }

    default List<Beer> beerDtosToBeers(List<BeerDTO> beerDtos) {
        return beerDtos.stream().map(this::beerDtoToBeer).toList();
    }
}
