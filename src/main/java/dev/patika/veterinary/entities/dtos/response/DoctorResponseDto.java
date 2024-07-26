package dev.patika.veterinary.entities.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorResponseDto {
    private long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String city;
}
