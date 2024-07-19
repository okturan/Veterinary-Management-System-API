package dev.patika.veterinary.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.dtos.request.AnimalRequestDto;
import dev.patika.veterinary.entities.dtos.response.AnimalResponseDto;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    AnimalMapper INSTANCE = Mappers.getMapper(AnimalMapper.class);

    @Mapping(source = "customer", target = "customer")
    AnimalResponseDto animalToAnimalResponseDto(Animal animal);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "vaccinations", ignore = true)
    Animal updateAnimalFromDto(AnimalRequestDto animalRequestDto, @MappingTarget Animal animal);

}
