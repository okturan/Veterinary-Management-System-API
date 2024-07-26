package dev.patika.veterinary;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Appointment;
import dev.patika.veterinary.entities.Availability;
import dev.patika.veterinary.entities.Doctor;
import dev.patika.veterinary.entities.Owner;
import dev.patika.veterinary.entities.Vaccination;
import dev.patika.veterinary.entities.Vaccine;
import dev.patika.veterinary.repositories.AnimalRepository;
import dev.patika.veterinary.repositories.AppointmentRepository;
import dev.patika.veterinary.repositories.AvailabilityRepository;
import dev.patika.veterinary.repositories.DoctorRepository;
import dev.patika.veterinary.repositories.OwnerRepository;
import dev.patika.veterinary.repositories.VaccinationRepository;
import dev.patika.veterinary.repositories.VaccineRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final OwnerRepository ownerRepository;
    private final AnimalRepository animalRepository;
    private final DoctorRepository doctorRepository;
    private final AvailabilityRepository availabilityRepository;
    private final VaccineRepository vaccineRepository;
    private final VaccinationRepository vaccinationRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public void run(String... args) {
        // Add Owners with mixed Turkish and English names
        String[] ownerNames = {
                "Ali Smith", "Fatma Johnson", "Mehmet Brown", "Elif Taylor", "Can Williams",
                "Aylin Davis", "Emre Miller", "Zeynep Wilson", "Oğuz Anderson", "Esra Thomas",
                "Burak Martin", "Leyla Jackson", "Serkan White", "Derya Harris", "Cem Clark",
                "Buse Lewis", "Efe Robinson", "Seda Young", "Engin Hall", "Pınar Allen"};

        for (int i = 0; i < ownerNames.length; i++) {
            Owner owner = new Owner();
            owner.setName(ownerNames[i]);
            owner.setPhone("555-" + (1000 + i + 1)); // Adjusted for unique phone numbers
            owner.setEmail("owner" + (i + 1) + "@example.com");
            owner.setAddress((i * 10) + " Main St");
            owner.setCity("City " + (i % 5 + 1)); // 5 different cities
            ownerRepository.save(owner);
        }

        // Add Animals
        String[] animalNames = {"Rex", "Mittens", "Buddy", "Whiskers", "Charlie",
                "Daisy", "Cooper", "Luna", "Rocky", "Coco", "Max", "Bella", "Oliver", "Toby", "Lucy",
                "Lola", "Chester", "Milo", "Nala", "Duke", "Sadie"};
        String[] species = {"Dog", "Cat", "Dog", "Cat", "Dog",
                "Cat", "Dog", "Cat", "Dog", "Cat", "Dog", "Cat", "Dog", "Cat", "Dog",
                "Cat", "Dog", "Cat", "Dog", "Cat", "Dog"};
        String[] breeds = {"Labrador", "Siamese", "Beagle", "Persian", "Poodle",
                "Bengal", "Bulldog", "Maine Coon", "Boxer", "Ragdoll", "Chihuahua", "Sphynx", "Dachshund", "Scottish Fold", "Doberman",
                "Beagle", "Bichon Frise", "Shih Tzu", "Golden Retriever", "Great Dane"};

        for (int i = 0; i < animalNames.length; i++) {
            Animal animal = new Animal();
            animal.setName(animalNames[i]);
            animal.setSpecies(species[i % species.length]);
            animal.setBreed(breeds[i % breeds.length]);
            animal.setGender(i % 2 == 0 ? "Male" : "Female");
            animal.setColour(i % 2 == 0 ? "Black" : "White");
            animal.setDateOfBirth(LocalDate.of(2020, 1, 1)
                                           .minusYears(i % 5)); // Vary the year slightly
            animal.setOwner(ownerRepository.findById((long) ((i % ownerNames.length) + 1))
                                           .orElse(null)); // Assign owners in pairs
            animalRepository.save(animal);
        }

        // Add Doctors
        for (int i = 1; i <= 10; i++) {
            Doctor doctor = new Doctor();
            doctor.setName("Dr. " + (i % 2 == 0 ? "Brown" : "Smith") + " " + i);
            doctor.setPhone("555-4" + (3000 + i));
            doctor.setEmail("dr." + i + "@example.com");
            doctor.setAddress((i * 20) + " Oak St");
            doctor.setCity("City " + (i % 5 + 1)); // 5 different cities
            doctorRepository.save(doctor);
        }

        // Add Availability for Doctors
        for (int i = 1; i <= 10; i++) {
            Doctor doctor = doctorRepository.findById((long) i)
                                            .orElse(null);
            for (int j = 1; j <= 5; j++) {
                Availability availability = new Availability();
                availability.setDoctor(doctor);
                availability.setDate(LocalDate.of(2023, 12, j)); // Available on Dec 1, 2, 3, 4, 5
                availabilityRepository.save(availability);
            }
        }

        // Add Vaccines
        String[] vaccineNames = {"Rabies Vaccine", "Distemper Vaccine", "Parvo Vaccine", "Bordetella Vaccine", "Lyme Vaccine",
                "Leptospirosis Vaccine", "Canine Influenza Vaccine", "Feline Leukemia Vaccine", "Feline Calicivirus Vaccine", "Feline Rhinotracheitis Vaccine"};
        String[] vaccineCodes = {"RV-01", "DV-01", "PV-01", "BV-01", "LV-01",
                "LV-02", "CIV-01", "FELV-01", "FECV-01", "FRV-01"};
        Period[] efficacyPeriods = {Period.ofYears(1), Period.ofYears(3), Period.ofYears(1), Period.ofYears(
                2), Period.ofYears(1),
                Period.ofYears(1), Period.ofYears(1), Period.ofYears(1), Period.ofYears(1), Period.ofYears(1)};

        for (int i = 0; i < vaccineNames.length; i++) {
            Vaccine vaccine = new Vaccine();
            vaccine.setName(vaccineNames[i]);
            vaccine.setCode(vaccineCodes[i]);
            vaccine.setEfficacyPeriod(efficacyPeriods[i]);
            vaccineRepository.save(vaccine);
        }

        // Add Vaccination for Animals
        for (int i = 1; i <= 20; i++) {
            Vaccination vaccination = new Vaccination();
            Animal animal = animalRepository.findById((long) i)
                                            .orElse(null);
            Vaccine vaccine = vaccineRepository.findById((long) ((i % 10) + 1))
                                               .orElse(null);
            vaccination.setAnimal(animal);
            vaccination.setVaccine(vaccine);
            vaccination.setVaccinationDate(LocalDate.now());
            vaccination.setNextDueDate(LocalDate.now()
                                                .plus(vaccine.getEfficacyPeriod()));
            vaccinationRepository.save(vaccination);
        }

        // Add Appointments
        for (int i = 1; i <= 20; i++) {
            Appointment appointment = new Appointment();
            Animal animal = animalRepository.findById((long) i)
                                            .orElse(null);
            Doctor doctor = doctorRepository.findById((long) ((i % 10) + 1))
                                            .orElse(null);
            appointment.setDoctor(doctor);
            appointment.setAnimal(animal);
            appointment.setAppointmentDate(
                    LocalDateTime.of(2023, 12, (i % 5) + 1, 14, 0)); // Appointments on Dec 1, 2, 3, 4, 5
            appointmentRepository.save(appointment);
        }
    }
}
