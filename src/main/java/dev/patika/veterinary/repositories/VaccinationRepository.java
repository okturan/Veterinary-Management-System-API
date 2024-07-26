package dev.patika.veterinary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

import dev.patika.veterinary.entities.Vaccination;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
    List<Vaccination> findByAnimalId(long id);

    List<Vaccination> findByNextDueDateBetween(LocalDate startDate, LocalDate endDate);
}
