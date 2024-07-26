package dev.patika.veterinary.entities.dtos.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentRequestDto {
    private long doctorId;
    private long animalId;
    private LocalDateTime appointmentDate;
}
