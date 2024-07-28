package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.util.List;

import dev.patika.veterinary.entities.Availability;
import dev.patika.veterinary.entities.Doctor;
import dev.patika.veterinary.entities.dtos.mappers.AvailabilityMapper;
import dev.patika.veterinary.entities.dtos.request.AvailabilityRequestDto;
import dev.patika.veterinary.entities.dtos.response.AvailabilityResponseDto;
import dev.patika.veterinary.repositories.AvailabilityRepository;
import dev.patika.veterinary.repositories.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityResponseDto save(Availability availability) {
        Availability savedAvailability = availabilityRepository.save(availability);
        return availabilityMapper.availabilityToAvailabilityResponseDto(savedAvailability);
    }

    public AvailabilityResponseDto addAvailabilityToDoctor(long id, AvailabilityRequestDto availabilityRequestDto) {
        Doctor doctor = doctorRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + id));
        availabilityRequestDto.setDoctorId(doctor.getId());

        Availability availability = availabilityMapper.availabilityFromDto(availabilityRequestDto);
        return availabilityMapper.availabilityToAvailabilityResponseDto(availabilityRepository.save(availability));
    }

    public AvailabilityResponseDto findById(long id) {
        Availability availability = availabilityRepository.findById(id)
                                                          .orElseThrow(() -> new EntityNotFoundException("Availability not found with: " + id));

        return availabilityMapper.availabilityToAvailabilityResponseDto(availability);
    }

    public List<AvailabilityResponseDto> findAll() {
        return availabilityRepository.findAll()
                                     .stream()
                                     .map(availabilityMapper::availabilityToAvailabilityResponseDto)
                                     .toList();
    }

    public List<AvailabilityResponseDto> findByDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + doctorId));
        return availabilityRepository.findByDoctor(doctor)
                                     .stream()
                                     .map(availabilityMapper::availabilityToAvailabilityResponseDto)
                                     .toList();
    }

    public AvailabilityResponseDto update(long id, AvailabilityRequestDto availabilityRequestDto) {
        Availability availability = availabilityRepository.findById(id)
                                                          .orElseThrow(() -> new EntityNotFoundException("Availability not found with id: " + id));
        Availability mergedAvailability = availabilityMapper.updateAvailabilityFromDto(availabilityRequestDto,
                                                                                       availability);
        return availabilityMapper.availabilityToAvailabilityResponseDto(
                availabilityRepository.save(mergedAvailability));
    }

    public void deleteById(long id) {
        availabilityRepository.findById(id)
                              .orElseThrow(() -> new EntityNotFoundException("Availability not found with id: " + id));
        availabilityRepository.deleteById(id);
    }

}
