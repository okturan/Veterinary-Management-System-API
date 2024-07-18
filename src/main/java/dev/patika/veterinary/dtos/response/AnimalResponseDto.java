package dev.patika.veterinary.dtos.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;

import dev.patika.veterinary.entities.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalResponseDto {

    private long id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;

    @JsonManagedReference
    private Customer customer;
}
