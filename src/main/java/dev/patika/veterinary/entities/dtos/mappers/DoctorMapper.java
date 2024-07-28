package dev.patika.veterinary.entities.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import dev.patika.veterinary.entities.Doctor;
import dev.patika.veterinary.entities.dtos.request.DoctorRequestDto;
import dev.patika.veterinary.entities.dtos.response.DoctorResponseDto;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorResponseDto doctorToDoctorResponseDto(Doctor doctor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "availabilities", ignore = true)
    Doctor updateDoctorFromDto(DoctorRequestDto doctorRequestDto, @MappingTarget Doctor doctor);
}
