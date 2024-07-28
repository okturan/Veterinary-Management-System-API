package dev.patika.veterinary.entities.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.dtos.request.AnimalRequestDto;
import dev.patika.veterinary.entities.dtos.response.AnimalResponseDto;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(source = "owner", target = "owner")
    AnimalResponseDto animalToAnimalResponseDto(Animal animal);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "vaccinations", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    void updateAnimalFromDto(AnimalRequestDto animalRequestDto, @MappingTarget Animal animal);

}
