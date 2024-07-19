package dev.patika.veterinary.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String species;

    @NotNull
    private String breed;

    @NotNull
    private String gender;

    @NotNull
    private String colour;

    @NotNull
    private LocalDate dateOfBirth;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "animal")
    private List<Vaccination> vaccinations;

}
