package dev.patika.veterinary.entities.dtos.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailabilityRequestDto {
    private long doctorId;
    private LocalDate date;
}
