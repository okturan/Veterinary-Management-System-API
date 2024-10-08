package dev.patika.veterinary.entities.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import dev.patika.veterinary.entities.Vaccine;
import dev.patika.veterinary.entities.dtos.request.VaccineRequestDto;
import dev.patika.veterinary.entities.dtos.response.VaccineResponseDto;

@Mapper(componentModel = "spring")
public interface VaccineMapper {

    VaccineResponseDto vaccineToVaccineResponseDto(Vaccine vaccine);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vaccinations", ignore = true)
    Vaccine updateVaccineFromDto(VaccineRequestDto vaccineRequestDto, @MappingTarget Vaccine vaccine);
}
