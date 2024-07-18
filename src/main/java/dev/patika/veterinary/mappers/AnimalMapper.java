package dev.patika.veterinary.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import dev.patika.veterinary.dtos.request.AnimalRequestDto;
import dev.patika.veterinary.dtos.response.AnimalResponseDto;
import dev.patika.veterinary.entities.Animal;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    AnimalMapper INSTANCE = Mappers.getMapper(AnimalMapper.class);

    AnimalResponseDto animalToAnimalResponseDto(Animal animal);

    Animal updateAnimalFromDto(AnimalRequestDto animalRequestDto, @MappingTarget Animal animal);

}
