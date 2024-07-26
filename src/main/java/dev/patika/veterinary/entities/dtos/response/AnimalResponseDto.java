package dev.patika.veterinary.entities.dtos.response;

import java.time.LocalDate;

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

    private InnerOwnerDto owner;

    @Getter
    @Setter
    public static class InnerOwnerDto {
        private long id;
        private String name;
        private String phone;
        private String email;
        private String address;
        private String city;
    }
}
