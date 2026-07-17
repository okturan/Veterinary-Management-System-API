package dev.patika.veterinary.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentService appointmentService;

    private Doctor doctor;
    private LocalDate availableDate;

    @BeforeEach
    void setUp() {
        availableDate = LocalDate.of(2026, 8, 12);
        Availability availability = new Availability();
        availability.setDate(availableDate);

        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setAppointments(new ArrayList<>());
        doctor.setAvailabilities(List.of(availability));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
    }

    @Test
    void rejectsAppointmentsThatAreNotOnTheHour() {
        AppointmentRequestDto request = requestAt(availableDate.atTime(9, 30));

        assertThrows(IllegalStateException.class, () -> appointmentService.save(request));
    }

    @Test
    void rejectsAppointmentsThatClashWithAnExistingSlot() {
        LocalDateTime requestedTime = availableDate.atTime(9, 0);
        when(appointmentRepository.existsByDoctorIdAndAppointmentDateAndIdNot(1L, requestedTime, -1L))
                .thenReturn(true);

        assertThrows(
                IllegalStateException.class,
                () -> appointmentService.save(requestAt(requestedTime))
        );
    }

    @Test
    void rejectsAppointmentsOutsideDoctorAvailability() {
        AppointmentRequestDto request = requestAt(availableDate.plusDays(1).atTime(9, 0));

        assertThrows(IllegalStateException.class, () -> appointmentService.save(request));
    }

    @Test
    void savesAnAvailableNonClashingAppointment() {
        AppointmentRequestDto request = requestAt(availableDate.atTime(9, 0));
        Animal animal = new Animal();
        Appointment appointment = new Appointment();
        AppointmentResponseDto response = new AppointmentResponseDto();

        when(animalRepository.findById(2L)).thenReturn(Optional.of(animal));
        when(appointmentMapper.appointmentRequestDtoToAppointment(request)).thenReturn(appointment);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(appointmentMapper.appointmentToAppointmentResponseDto(appointment)).thenReturn(response);

        assertSame(response, appointmentService.save(request));
        verify(appointmentRepository).save(appointment);
    }

    private AppointmentRequestDto requestAt(LocalDateTime appointmentDate) {
        AppointmentRequestDto request = new AppointmentRequestDto();
        request.setDoctorId(1L);
        request.setAnimalId(2L);
        request.setAppointmentDate(appointmentDate);
        return request;
    }
}
