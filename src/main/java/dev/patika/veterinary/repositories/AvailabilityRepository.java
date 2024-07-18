package dev.patika.veterinary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.patika.veterinary.entities.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}