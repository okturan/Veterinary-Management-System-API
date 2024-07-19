package dev.patika.veterinary.entities.dtos.request;

import java.time.Period;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaccineRequestDto {
    private String name;
    private String code;
    private Period efficacyPeriod;
}
