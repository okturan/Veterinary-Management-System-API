package dev.patika.veterinary.entities.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import dev.patika.veterinary.entities.Vaccination;
import dev.patika.veterinary.entities.dtos.request.VaccinationRequestDto;
import dev.patika.veterinary.entities.dtos.response.VaccinationResponseDto;

@Mapper(componentModel = "spring")
public interface VaccinationMapper {

    @Mapping(source = "vaccine.id", target = "vaccineId")
    @Mapping(source = "animal.id", target = "animalId")
    VaccinationResponseDto vaccinationToVaccinationResponseDto(Vaccination vaccination);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nextDueDate", ignore = true)
    @Mapping(source = "vaccineId", target = "vaccine.id")
    @Mapping(source = "animalId", target = "animal.id")
    Vaccination vaccinationFromDto(VaccinationRequestDto vaccinationRequestDto);
}
