package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Vaccination;
import dev.patika.veterinary.entities.Vaccine;
import dev.patika.veterinary.entities.dtos.request.VaccinationRequestDto;
import dev.patika.veterinary.entities.dtos.response.VaccinationResponseDto;
import dev.patika.veterinary.mappers.VaccinationMapper;
import dev.patika.veterinary.repositories.AnimalRepository;
import dev.patika.veterinary.repositories.VaccinationRepository;
import dev.patika.veterinary.repositories.VaccineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VaccinationService implements IService<Vaccination, VaccinationResponseDto, VaccinationRequestDto> {

    private final VaccinationRepository vaccinationRepository;
    private final AnimalRepository animalRepository;
    private final VaccineRepository vaccineRepository;
    private final VaccinationMapper vaccinationMapper;

    @Override
    public VaccinationResponseDto save(Vaccination vaccination) {
        Vaccination savedVaccination = vaccinationRepository.save(vaccination);
        return vaccinationMapper.vaccinationToVaccinationResponseDto(savedVaccination);
    }

    @Override
    public VaccinationResponseDto findById(long id) {
        Optional<Vaccination> optionalVaccination = vaccinationRepository.findById(id);
        if (optionalVaccination.isEmpty()) {
            throw new EntityNotFoundException("Vaccination not found with id: " + id);
        }
        return vaccinationMapper.vaccinationToVaccinationResponseDto(optionalVaccination.get());
    }

    @Override
    public List<VaccinationResponseDto> findAll() {
        return vaccinationRepository.findAll()
                                    .stream()
                                    .map(vaccinationMapper::vaccinationToVaccinationResponseDto)
                                    .toList();
    }

    @Override
    public VaccinationResponseDto update(long id, VaccinationRequestDto vaccinationRequestDto) {
        Optional<Vaccination> optionalVaccination = vaccinationRepository.findById(id);
        if (optionalVaccination.isEmpty()) {
            throw new EntityNotFoundException("Vaccination not found with id: " + id);
        }
        Vaccination mergedVaccination = vaccinationMapper.updateVaccinationFromDto(vaccinationRequestDto,
                                                                                   optionalVaccination.get());
        return vaccinationMapper.vaccinationToVaccinationResponseDto(vaccinationRepository.save(mergedVaccination));
    }

    @Override
    public void deleteById(long id) {
        if (!vaccinationRepository.existsById(id)) {
            throw new EntityNotFoundException("Vaccination not found with id: " + id);
        }
        vaccinationRepository.deleteById(id);
    }

    public VaccinationResponseDto saveForAnimal(long animalId, VaccinationRequestDto vaccinationRequestDto) {
        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);
        if (optionalAnimal.isEmpty()) {
            throw new EntityNotFoundException("Animal not found with id: " + animalId);
        }
        Animal animal = optionalAnimal.get();

        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(vaccinationRequestDto.getVaccineId());
        if (optionalVaccine.isEmpty()) {
            throw new EntityNotFoundException("Vaccine not found with id: " + vaccinationRequestDto.getVaccineId());
        }
        Vaccine vaccine = optionalVaccine.get();

        Vaccination vaccination = new Vaccination();
        vaccination.setVaccinationDate(LocalDate.now());
        vaccination.setNextDueDate(vaccination.getVaccinationDate()
                                              .plus(vaccine.getEfficacyPeriod()));
        vaccination.setAnimal(animal);
        vaccination.setVaccine(vaccine);

        return vaccinationMapper.vaccinationToVaccinationResponseDto(vaccinationRepository.save(vaccination));
    }

    public List<VaccinationResponseDto> findByAnimalId(long animalId) {
        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);
        if (optionalAnimal.isEmpty()) {
            throw new EntityNotFoundException("Animal not found with id: " + animalId);
        }

        return vaccinationRepository.findByAnimalId(animalId)
                                    .stream()
                                    .map(vaccinationMapper::vaccinationToVaccinationResponseDto)
                                    .toList();
    }
}
