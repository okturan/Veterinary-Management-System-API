package dev.patika.veterinary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.patika.veterinary.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}