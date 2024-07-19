package dev.patika.veterinary.entities.dtos.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaccinationResponseDto {
    private long id;
    private AnimalResponseDto animal;
    private VaccineResponseDto vaccine;
    private LocalDate vaccinationDate;
    private LocalDate nextDueDate;

}
