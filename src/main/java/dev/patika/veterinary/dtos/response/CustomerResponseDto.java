package dev.patika.veterinary.dtos.response;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

import dev.patika.veterinary.entities.Animal;
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

    @JsonBackReference
    private List<Animal> animals;
}
