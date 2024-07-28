package dev.patika.veterinary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import dev.patika.veterinary.entities.Availability;
import dev.patika.veterinary.entities.Doctor;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByDoctor(Doctor doctor);
}