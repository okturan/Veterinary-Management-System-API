package dev.patika.veterinary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

import dev.patika.veterinary.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a WHERE " +
           "(COALESCE(:startDate, a.appointmentDate) <= a.appointmentDate) AND " +
           "(COALESCE(:endDate, a.appointmentDate) >= a.appointmentDate) AND " +
           "(COALESCE(:doctorId, a.doctor.id) = a.doctor.id) AND " +
           "(COALESCE(:animalId, a.animal.id) = a.animal.id)")
    List<Appointment> findAppointmentsFlexible(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate,
                                               @Param("doctorId") Long doctorId,
                                               @Param("animalId") Long animalId);
}