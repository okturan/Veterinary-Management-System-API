package dev.patika.veterinary.dtos.request;

import dev.patika.veterinary.entities.Animal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDto {

    private String name;
    private String phone;
    private String email;
    private String address;
    private String city;

    private Animal animal;
}
