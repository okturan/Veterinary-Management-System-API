package dev.patika.veterinary.entities.dtos.response;

import java.time.Period;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaccineResponseDto {
    private long id;
    private String name;
    private String code;
    private Period efficacyPeriod;
}
