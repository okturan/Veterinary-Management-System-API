package dev.patika.veterinary.entities.dtos.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentResponseDto {
    private long id;
    private long doctorId;
    private long animalId;
    private LocalDateTime appointmentDate;
}
