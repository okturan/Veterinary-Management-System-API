package dev.patika.veterinary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.patika.veterinary.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
