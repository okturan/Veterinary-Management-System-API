package dev.patika.veterinary.entities.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import dev.patika.veterinary.entities.Appointment;
import dev.patika.veterinary.entities.dtos.request.AppointmentRequestDto;
import dev.patika.veterinary.entities.dtos.response.AppointmentResponseDto;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "animal.id", target = "animalId")
    AppointmentResponseDto appointmentToAppointmentResponseDto(Appointment appointment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "animal.id", source = "animalId")
    Appointment appointmentRequestDtoToAppointment(AppointmentRequestDto appointmentRequestDto);
}
