package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.util.List;

import dev.patika.veterinary.entities.Vaccine;
import dev.patika.veterinary.entities.dtos.mappers.VaccineMapper;
import dev.patika.veterinary.entities.dtos.request.VaccineRequestDto;
import dev.patika.veterinary.entities.dtos.response.VaccineResponseDto;
import dev.patika.veterinary.repositories.VaccineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final VaccineMapper vaccineMapper;

    public VaccineResponseDto save(Vaccine vaccine) {
        Vaccine savedVaccine = vaccineRepository.save(vaccine);
        return vaccineMapper.vaccineToVaccineResponseDto(savedVaccine);
    }

    public VaccineResponseDto findById(long id) {
        Vaccine vaccine = vaccineRepository.findById(id)
                                           .orElseThrow(() -> new EntityNotFoundException("Vaccine not found with id: " + id));
        return vaccineMapper.vaccineToVaccineResponseDto(vaccine);
    }

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

    public VaccineResponseDto update(long id, VaccineRequestDto vaccineRequestDto) {
        Vaccine vaccine = vaccineRepository.findById(id)
                                           .orElseThrow(() -> new EntityNotFoundException("Vaccine not found with id: " + id));

        Vaccine mergedVaccine = vaccineMapper.updateVaccineFromDto(vaccineRequestDto, vaccine);
        return vaccineMapper.vaccineToVaccineResponseDto(vaccineRepository.save(mergedVaccine));
    }

    public void deleteById(long id) {
        vaccineRepository.findById(id)
                         .orElseThrow(() -> new EntityNotFoundException("Vaccine not found with id: " + id));
        vaccineRepository.deleteById(id);
    }
}
