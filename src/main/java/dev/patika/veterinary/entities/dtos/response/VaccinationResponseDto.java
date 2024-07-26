package dev.patika.veterinary.entities.dtos.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaccinationResponseDto {
    private long id;
    private long animalId;
    private long vaccineId;
    private LocalDate vaccinationDate;
    private LocalDate nextDueDate;

}
