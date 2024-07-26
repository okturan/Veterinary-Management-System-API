package dev.patika.veterinary.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import dev.patika.veterinary.entities.Doctor;
import dev.patika.veterinary.entities.dtos.request.AvailabilityRequestDto;
import dev.patika.veterinary.entities.dtos.request.DoctorRequestDto;
import dev.patika.veterinary.entities.dtos.response.AvailabilityResponseDto;
import dev.patika.veterinary.entities.dtos.response.DoctorResponseDto;
import dev.patika.veterinary.services.AppointmentService;
import dev.patika.veterinary.services.AvailabilityService;
import dev.patika.veterinary.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final AvailabilityService availabilityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponseDto create(@Valid @RequestBody Doctor doctor) {
        return doctorService.save(doctor);
    }

    @PostMapping("/{doctorId}/availabilities")
    @ResponseStatus(HttpStatus.CREATED)
    public AvailabilityResponseDto addAvailabilityToDoctor(@PathVariable long doctorId,
                                                           @Valid @RequestBody AvailabilityRequestDto availabilityRequestDto)
    {
        return availabilityService.addAvailabilityToDoctor(doctorId, availabilityRequestDto);
    }

    @GetMapping("/{id}")
    public DoctorResponseDto getById(@PathVariable long id) {
        return doctorService.findById(id);
    }

    @GetMapping
    public List<DoctorResponseDto> getAll() {
        return doctorService.findAll();
    }

    @PutMapping("/{id}")
    public DoctorResponseDto update(@PathVariable long id, @Valid @RequestBody DoctorRequestDto doctorRequestDto) {
        return doctorService.update(id, doctorRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        doctorService.deleteById(id);
    }
}
