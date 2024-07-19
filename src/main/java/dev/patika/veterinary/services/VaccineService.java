package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import dev.patika.veterinary.entities.Vaccine;
import dev.patika.veterinary.entities.dtos.request.VaccineRequestDto;
import dev.patika.veterinary.entities.dtos.response.VaccineResponseDto;
import dev.patika.veterinary.mappers.VaccineMapper;
import dev.patika.veterinary.repositories.VaccineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VaccineService implements IService<Vaccine, VaccineResponseDto, VaccineRequestDto> {

    private final VaccineRepository vaccineRepository;
    private final VaccineMapper vaccineMapper;

    @Override
    public VaccineResponseDto save(Vaccine vaccine) {
        Vaccine savedVaccine = vaccineRepository.save(vaccine);
        return vaccineMapper.vaccineToVaccineResponseDto(savedVaccine);
    }

    @Override
    public VaccineResponseDto findById(long id) {
        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(id);
        if (optionalVaccine.isEmpty()) {
            throw new EntityNotFoundException("Vaccine not found with id: " + id);
        }
        return vaccineMapper.vaccineToVaccineResponseDto(optionalVaccine.get());
    }

    @Override
    public List<VaccineResponseDto> findAll() {
        return vaccineRepository.findAll()
                                .stream()
                                .map(vaccineMapper::vaccineToVaccineResponseDto)
                                .toList();
    }

    public List<VaccineResponseDto> findByName(String name) {
        return vaccineRepository.findByNameContainsIgnoreCase(name)
                                .stream()
                                .map(vaccineMapper::vaccineToVaccineResponseDto)
                                .toList();
    }

    @Override
    public VaccineResponseDto update(long id, VaccineRequestDto vaccineRequestDto) {
        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(id);
        if (optionalVaccine.isEmpty()) {
            throw new EntityNotFoundException("Vaccine not found with id: " + id);
        }
        Vaccine mergedVaccine = vaccineMapper.updateVaccineFromDto(vaccineRequestDto, optionalVaccine.get());
        return vaccineMapper.vaccineToVaccineResponseDto(vaccineRepository.save(mergedVaccine));
    }

    @Override
    public void deleteById(long id) {
        if (!vaccineRepository.existsById(id)) {
            throw new EntityNotFoundException("Vaccine not found with id: " + id);
        }
        vaccineRepository.deleteById(id);
    }
}
