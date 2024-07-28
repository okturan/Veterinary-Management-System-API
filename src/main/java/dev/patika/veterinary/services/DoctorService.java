package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.util.List;

import dev.patika.veterinary.entities.Doctor;
import dev.patika.veterinary.entities.dtos.mappers.DoctorMapper;
import dev.patika.veterinary.entities.dtos.request.DoctorRequestDto;
import dev.patika.veterinary.entities.dtos.response.DoctorResponseDto;
import dev.patika.veterinary.repositories.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorResponseDto save(Doctor doctor) {
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.doctorToDoctorResponseDto(savedDoctor);
    }

    public DoctorResponseDto findById(long id) {
        Doctor doctor = doctorRepository.findById(id)
                                        .orElseThrow(EntityNotFoundException::new);

        return doctorMapper.doctorToDoctorResponseDto(doctor);
    }

    public List<DoctorResponseDto> findAll() {
        return doctorRepository.findAll()
                               .stream()
                               .map(doctorMapper::doctorToDoctorResponseDto)
                               .toList();
    }

    public DoctorResponseDto update(long id, DoctorRequestDto doctorRequestDto) {
        Doctor doctor = doctorRepository.findById(id)
                                        .orElseThrow(EntityNotFoundException::new);
        Doctor mergedDoctor = doctorMapper.updateDoctorFromDto(doctorRequestDto, doctor);
        return doctorMapper.doctorToDoctorResponseDto(doctorRepository.save(mergedDoctor));
    }

    public void deleteById(long id) {
        doctorRepository.findById(id)
                        .orElseThrow(EntityNotFoundException::new);
        doctorRepository.deleteById(id);
    }
}
