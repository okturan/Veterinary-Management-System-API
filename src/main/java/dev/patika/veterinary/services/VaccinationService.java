package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Vaccination;
import dev.patika.veterinary.entities.Vaccine;
import dev.patika.veterinary.entities.dtos.mappers.VaccinationMapper;
import dev.patika.veterinary.entities.dtos.request.VaccinationRequestDto;
import dev.patika.veterinary.entities.dtos.response.VaccinationResponseDto;
import dev.patika.veterinary.repositories.AnimalRepository;
import dev.patika.veterinary.repositories.VaccinationRepository;
import dev.patika.veterinary.repositories.VaccineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VaccinationService {

    private final VaccinationRepository vaccinationRepository;
    private final AnimalRepository animalRepository;
    private final VaccineRepository vaccineRepository;
    private final VaccinationMapper vaccinationMapper;

    public VaccinationResponseDto save(VaccinationRequestDto vaccinationRequestDto) {
        LocalDate vaccinationDate = requireVaccinationDate(vaccinationRequestDto);
        Animal animal = validateAndFetchAnimal(vaccinationRequestDto.getAnimalId());
        Vaccine vaccine = validateAndFetchVaccine(vaccinationRequestDto.getVaccineId());

        validateNewVaccination(animal, vaccine);

        Vaccination vaccination = vaccinationMapper.vaccinationFromDto(vaccinationRequestDto);
        vaccination.setAnimal(animal);
        updateVaccinationDetails(vaccination, vaccine, vaccinationDate);

        return saveVaccinationAndConvert(vaccination);
    }

    public VaccinationResponseDto findById(long id) {
        return vaccinationMapper.vaccinationToVaccinationResponseDto(getVaccinationById(id));
    }

    public List<VaccinationResponseDto> findAll() {
        return vaccinationRepository.findAll()
                                    .stream()
                                    .map(vaccinationMapper::vaccinationToVaccinationResponseDto)
                                    .toList();
    }

    public List<VaccinationResponseDto> findByNextDueDateBetween(LocalDate startDate, LocalDate endDate) {
        return vaccinationRepository.findByNextDueDateBetween(startDate, endDate)
                                    .stream()
                                    .map(vaccinationMapper::vaccinationToVaccinationResponseDto)
                                    .toList();
    }

    public VaccinationResponseDto update(long id, VaccinationRequestDto vaccinationRequestDto) {
        LocalDate vaccinationDate = requireVaccinationDate(vaccinationRequestDto);
        Vaccination existingVaccination = getVaccinationById(id);
        Vaccine newVaccine = validateAndFetchVaccine(vaccinationRequestDto.getVaccineId());

        validateVaccinationUpdate(id, existingVaccination.getAnimal(), newVaccine, existingVaccination.getVaccine());

        updateVaccinationDetails(existingVaccination, newVaccine, vaccinationDate);

        return saveVaccinationAndConvert(existingVaccination);
    }

    public void deleteById(long id) {
        getVaccinationById(id);
        vaccinationRepository.deleteById(id);
    }

    public VaccinationResponseDto saveVaccinationForAnimal(long animalId, VaccinationRequestDto vaccinationRequestDto) {
        validateAndFetchAnimal(animalId);
        vaccinationRequestDto.setAnimalId(animalId);
        return save(vaccinationRequestDto);
    }

    public List<VaccinationResponseDto> findByAnimalId(long animalId) {
        validateAndFetchAnimal(animalId);
        return vaccinationRepository.findByAnimalId(animalId)
                                    .stream()
                                    .map(vaccinationMapper::vaccinationToVaccinationResponseDto)
                                    .toList();
    }

    private Vaccination getVaccinationById(long id) {
        return vaccinationRepository.findById(id)
                                    .orElseThrow(() -> new EntityNotFoundException("Vaccination not found with id: " + id));
    }

    private Animal validateAndFetchAnimal(long id) {
        return animalRepository.findById(id)
                               .orElseThrow(() -> new EntityNotFoundException("Animal not found with id: " + id));
    }

    private Vaccine validateAndFetchVaccine(long id) {
        return vaccineRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Vaccine not found with id: " + id));
    }

    private void validateVaccinationUpdate(long currentVaccinationId, Animal animal, Vaccine newVaccine, Vaccine oldVaccine) {
        if (newVaccine.getId() != oldVaccine.getId()) {
            validateNewVaccination(animal, newVaccine, currentVaccinationId);
        }
    }

    private void validateNewVaccination(Animal animal, Vaccine vaccine) {
        validateNewVaccination(animal, vaccine, -1);
    }

    private void validateNewVaccination(Animal animal, Vaccine vaccine, long excludeVaccinationId) {
        boolean hasValidVaccination = vaccinationRepository
                .existsByAnimalIdAndVaccineIdAndNextDueDateAfterAndIdNot(
                        animal.getId(), vaccine.getId(), LocalDate.now(), excludeVaccinationId);

        if (hasValidVaccination) {
            throw new IllegalStateException("A valid vaccination for this vaccine already exists for the animal");
        }
    }

    private void updateVaccinationDetails(Vaccination vaccination, Vaccine vaccine, LocalDate vaccinationDate) {
        vaccination.setVaccine(vaccine);
        vaccination.setVaccinationDate(vaccinationDate);
        vaccination.setNextDueDate(vaccinationDate.plus(vaccine.getEfficacyPeriod()));
    }

    private LocalDate requireVaccinationDate(VaccinationRequestDto request) {
        if (request.getVaccinationDate() == null) {
            throw new IllegalArgumentException("Vaccination date is required");
        }
        return request.getVaccinationDate();
    }

    private VaccinationResponseDto saveVaccinationAndConvert(Vaccination vaccination) {
        Vaccination savedVaccination = vaccinationRepository.save(vaccination);
        return vaccinationMapper.vaccinationToVaccinationResponseDto(savedVaccination);
    }
}
