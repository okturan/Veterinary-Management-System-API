package dev.patika.veterinary.entities.dtos.request;

import java.time.Period;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaccineRequestDto {
    private String name;
    private String code;
    @Schema(type = "string", example = "P1Y", description = "ISO-8601 period until the next dose is due")
    private Period efficacyPeriod;
}
