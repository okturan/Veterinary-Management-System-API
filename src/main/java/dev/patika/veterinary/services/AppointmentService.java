package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Appointment;
import dev.patika.veterinary.entities.Availability;
import dev.patika.veterinary.entities.Doctor;
import dev.patika.veterinary.entities.dtos.mappers.AppointmentMapper;
import dev.patika.veterinary.entities.dtos.request.AppointmentRequestDto;
import dev.patika.veterinary.entities.dtos.response.AppointmentResponseDto;
import dev.patika.veterinary.repositories.AnimalRepository;
import dev.patika.veterinary.repositories.AppointmentRepository;
import dev.patika.veterinary.repositories.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final AnimalRepository animalRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentResponseDto save(AppointmentRequestDto appointmentRequestDto) {
        Doctor doctor = validateAndGetDoctor(appointmentRequestDto.getDoctorId());
        validateAppointment(doctor, appointmentRequestDto.getAppointmentDate());

        animalRepository.findById(appointmentRequestDto.getAnimalId())
                        .orElseThrow(EntityNotFoundException::new);

        Appointment appointment = appointmentMapper.appointmentRequestDtoToAppointment(appointmentRequestDto);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.appointmentToAppointmentResponseDto(savedAppointment);
    }

    public AppointmentResponseDto update(long appointmentId, AppointmentRequestDto appointmentRequestDto) {
        // Retrieve existing appointment
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                                                               .orElseThrow(() -> new EntityNotFoundException(
                                                                       "Appointment not found with ID: " +
                                                                       appointmentId));

        Doctor doctor = validateAndGetDoctor(appointmentRequestDto.getDoctorId());

        validateAppointment(doctor, appointmentRequestDto.getAppointmentDate());

        Animal animal = animalRepository.findById(appointmentRequestDto.getAnimalId())
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                "Animal not found with ID: " + appointmentRequestDto.getAnimalId()));

        existingAppointment.setDoctor(doctor);
        existingAppointment.setAnimal(animal);
        existingAppointment.setAppointmentDate(appointmentRequestDto.getAppointmentDate());

        Appointment savedAppointment = appointmentRepository.save(existingAppointment);
        return appointmentMapper.appointmentToAppointmentResponseDto(savedAppointment);
    }

    private Doctor validateAndGetDoctor(Long doctorId) {
        return doctorRepository.findById(doctorId)
                               .orElseThrow(EntityNotFoundException::new);
    }

    private void validateAppointment(Doctor doctor, LocalDateTime requestedAppointment) {
        List<LocalDateTime> appointmentDates = doctor.getAppointments()
                                                     .stream()
                                                     .map(Appointment::getAppointmentDate)
                                                     .toList();

        List<LocalDate> availabilityDates = doctor.getAvailabilities()
                                                  .stream()
                                                  .map(Availability::getDate)
                                                  .toList();

        if (requestedAppointment.getMinute() != 0) {
            throw new IllegalStateException("Appointments can only be scheduled on the hour.");
        }

        boolean notClashing = !appointmentDates.contains(requestedAppointment);
        boolean isAvailable = availabilityDates.contains(requestedAppointment.toLocalDate());

        if (!notClashing) {
            throw new IllegalStateException("There is a clashing appointment at the same date and hour");
        }
        if (!isAvailable) {
            throw new IllegalStateException("Doctor is not available on that date");
        }
    }

    public AppointmentResponseDto findById(long id) {
        Appointment appointment = appointmentRepository.findById(id)
                                                       .orElseThrow(EntityNotFoundException::new);
        return appointmentMapper.appointmentToAppointmentResponseDto(appointment);
    }


    public List<AppointmentResponseDto> findAllWithFilters(LocalDate startDate, LocalDate endDate, Long doctorId,
                                                           Long animalId)
    {
        LocalDateTime startDateTime = startDate == null ? null : startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate == null ? null : endDate.atStartOfDay();
        return appointmentRepository.findAppointmentsFlexible(startDateTime, endDateTime,
                                                                                      doctorId, animalId)
                                    .stream()
                                    .map(appointmentMapper::appointmentToAppointmentResponseDto)
                                    .toList();
    }

    public List<AppointmentResponseDto> findAll() {
        return appointmentRepository.findAll()
                                    .stream()
                                    .map(appointmentMapper::appointmentToAppointmentResponseDto)
                                    .toList();
    }

    public void deleteById(long id) {
        appointmentRepository.findById(id)
                             .orElseThrow(EntityNotFoundException::new);
        appointmentRepository.deleteById(id);
    }
}
