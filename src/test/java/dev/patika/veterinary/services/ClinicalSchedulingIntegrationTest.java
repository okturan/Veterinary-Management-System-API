package dev.patika.veterinary.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Appointment;
import dev.patika.veterinary.entities.Availability;
import dev.patika.veterinary.entities.Doctor;
import dev.patika.veterinary.entities.Vaccination;
import dev.patika.veterinary.entities.Vaccine;
import dev.patika.veterinary.entities.dtos.request.AppointmentRequestDto;
import dev.patika.veterinary.entities.dtos.request.VaccinationRequestDto;
import dev.patika.veterinary.entities.dtos.response.AppointmentResponseDto;
import dev.patika.veterinary.entities.dtos.response.VaccinationResponseDto;
import dev.patika.veterinary.repositories.AnimalRepository;
import dev.patika.veterinary.repositories.AppointmentRepository;
import dev.patika.veterinary.repositories.AvailabilityRepository;
import dev.patika.veterinary.repositories.DoctorRepository;
import dev.patika.veterinary.repositories.VaccinationRepository;
import dev.patika.veterinary.repositories.VaccineRepository;
import jakarta.persistence.EntityManager;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ClinicalSchedulingIntegrationTest {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private VaccinationService vaccinationService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsAvailableAppointmentsRejectsClashesAndAllowsSelfPreservingUpdates() {
        LocalDate availableDate = LocalDate.now().plusDays(7);
        Doctor doctor = saveDoctorWithAvailability(availableDate);
        Animal animal = saveAnimal();
        LocalDateTime slot = availableDate.atTime(9, 0);
        AppointmentRequestDto request = appointmentRequest(doctor.getId(), animal.getId(), slot);

        AppointmentResponseDto created = appointmentService.save(request);
        Appointment persisted = appointmentRepository.findById(created.getId()).orElseThrow();

        assertEquals(doctor.getId(), persisted.getDoctor().getId());
        assertEquals(animal.getId(), persisted.getAnimal().getId());
        assertEquals(slot, persisted.getAppointmentDate());
        assertThrows(IllegalStateException.class, () -> appointmentService.save(request));

        AppointmentResponseDto unchangedSlot = appointmentService.update(created.getId(), request);
        assertEquals(created.getId(), unchangedSlot.getId());
        assertEquals(slot, unchangedSlot.getAppointmentDate());
    }

    @Test
    void persistsRequestedVaccinationDateCalculatesDueDateAndRejectsActiveDuplicate() {
        Animal animal = saveAnimal();
        Vaccine vaccine = saveVaccine(Period.ofYears(1));
        LocalDate administeredOn = LocalDate.now().minusDays(1);
        VaccinationRequestDto request = vaccinationRequest(animal.getId(), vaccine.getId(), administeredOn);

        VaccinationResponseDto created = vaccinationService.save(request);
        Vaccination persisted = vaccinationRepository.findById(created.getId()).orElseThrow();

        assertEquals(administeredOn, persisted.getVaccinationDate());
        assertEquals(administeredOn.plusYears(1), persisted.getNextDueDate());
        assertEquals(animal.getId(), persisted.getAnimal().getId());

        entityManager.flush();
        entityManager.clear();

        assertThrows(IllegalStateException.class, () -> vaccinationService.save(request));
    }

    @Test
    void rejectsVaccinationsWithoutAnAdministrationDate() {
        Animal animal = saveAnimal();
        Vaccine vaccine = saveVaccine(Period.ofMonths(6));
        VaccinationRequestDto request = vaccinationRequest(animal.getId(), vaccine.getId(), null);

        assertThrows(IllegalArgumentException.class, () -> vaccinationService.save(request));
        assertEquals(0, vaccinationRepository.count());
    }

    private Doctor saveDoctorWithAvailability(LocalDate date) {
        Doctor doctor = new Doctor();
        doctor.setName("Dr. Integration");
        doctor.setPhone("555-0100");
        doctor.setEmail("integration-doctor@example.test");
        doctor.setAddress("1 Test Street");
        doctor.setCity("Test City");
        Doctor savedDoctor = doctorRepository.save(doctor);

        Availability availability = new Availability();
        availability.setDoctor(savedDoctor);
        availability.setDate(date);
        availabilityRepository.save(availability);

        entityManager.flush();
        entityManager.clear();
        return doctorRepository.findById(savedDoctor.getId()).orElseThrow();
    }

    private Animal saveAnimal() {
        Animal animal = new Animal();
        animal.setName("Integration Patient");
        animal.setSpecies("Dog");
        animal.setBreed("Mixed");
        animal.setGender("Female");
        animal.setColour("Brown");
        animal.setDateOfBirth(LocalDate.of(2021, 1, 1));
        return animalRepository.save(animal);
    }

    private Vaccine saveVaccine(Period efficacyPeriod) {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Integration Vaccine");
        vaccine.setCode("INT-" + efficacyPeriod.toTotalMonths());
        vaccine.setEfficacyPeriod(efficacyPeriod);
        return vaccineRepository.save(vaccine);
    }

    private AppointmentRequestDto appointmentRequest(long doctorId, long animalId, LocalDateTime slot) {
        AppointmentRequestDto request = new AppointmentRequestDto();
        request.setDoctorId(doctorId);
        request.setAnimalId(animalId);
        request.setAppointmentDate(slot);
        return request;
    }

    private VaccinationRequestDto vaccinationRequest(long animalId, long vaccineId, LocalDate date) {
        VaccinationRequestDto request = new VaccinationRequestDto();
        request.setAnimalId(animalId);
        request.setVaccineId(vaccineId);
        request.setVaccinationDate(date);
        return request;
    }
}
