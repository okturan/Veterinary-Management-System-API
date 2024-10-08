package dev.patika.veterinary.entities.dtos.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaccinationRequestDto {
    private long vaccineId;
    private long animalId;
    private LocalDate vaccinationDate;
}
