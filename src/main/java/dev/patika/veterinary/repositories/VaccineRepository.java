package dev.patika.veterinary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import dev.patika.veterinary.entities.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByNameContainsIgnoreCase(String name);
}