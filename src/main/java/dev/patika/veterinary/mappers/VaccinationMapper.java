package dev.patika.veterinary.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import dev.patika.veterinary.entities.Vaccination;
import dev.patika.veterinary.entities.dtos.request.VaccinationRequestDto;
import dev.patika.veterinary.entities.dtos.response.VaccinationResponseDto;

@Mapper(componentModel = "spring")
public interface VaccinationMapper {

    VaccinationMapper INSTANCE = Mappers.getMapper(VaccinationMapper.class);

    VaccinationResponseDto vaccinationToVaccinationResponseDto(Vaccination vaccination);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "vaccineId", target = "vaccine.id")
    @Mapping(target = "vaccinationDate", ignore = true)
    @Mapping(target = "nextDueDate", ignore = true)
    @Mapping(target = "animal", ignore = true)
    Vaccination updateVaccinationFromDto(VaccinationRequestDto vaccinationRequestDto,
                                         @MappingTarget Vaccination vaccination);
}
