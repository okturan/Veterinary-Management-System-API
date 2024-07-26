package dev.patika.veterinary.entities.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import dev.patika.veterinary.entities.Availability;
import dev.patika.veterinary.entities.dtos.request.AvailabilityRequestDto;
import dev.patika.veterinary.entities.dtos.response.AvailabilityResponseDto;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    AvailabilityMapper INSTANCE = Mappers.getMapper(AvailabilityMapper.class);

    @Mapping(source = "doctor.id", target = "doctorId")
    AvailabilityResponseDto availabilityToAvailabilityResponseDto(Availability availability);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "doctorId", target = "doctor.id")
    Availability updateAvailabilityFromDto(AvailabilityRequestDto availabilityRequestDto, @MappingTarget Availability availability);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "doctorId", target = "doctor.id")
    Availability availabilityFromDto(AvailabilityRequestDto availabilityRequestDto);

}
