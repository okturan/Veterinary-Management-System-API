package dev.patika.veterinary.entities.dtos.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailabilityResponseDto {
    private long id;
    private long doctorId;
    private LocalDate date;
}
