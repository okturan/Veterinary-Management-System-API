package dev.patika.veterinary.entities.dtos.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDto {

    private long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String city;

    private List<InnerAnimalDto> animals;

    @Getter
    @Setter
    public static class InnerAnimalDto {
        private long id;
        private String name;
        private String species;
        private String breed;
        private String gender;
        private String colour;
        private LocalDate dateOfBirth;
    }
}
