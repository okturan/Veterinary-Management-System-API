package dev.patika.veterinary.entities.dtos.request;

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
}
