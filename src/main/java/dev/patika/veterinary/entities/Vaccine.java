package dev.patika.veterinary.entities;

import java.time.Period;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;
    @NotNull
    private String code;
    @NotNull
    private Period efficacyPeriod;

    @OneToMany(mappedBy = "vaccine")
    private List<Vaccination> vaccinations;
}
