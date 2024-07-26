package dev.patika.veterinary.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import dev.patika.veterinary.entities.dtos.request.AppointmentRequestDto;
import dev.patika.veterinary.entities.dtos.response.AppointmentResponseDto;
import dev.patika.veterinary.services.AppointmentService;
import dev.patika.veterinary.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponseDto create(@Valid @RequestBody AppointmentRequestDto appointment) {
        return appointmentService.save(appointment);
    }

    @GetMapping("/{id}")
    public AppointmentResponseDto getById(@PathVariable long id) {
        return appointmentService.findById(id);
    }

    @GetMapping
    public List<AppointmentResponseDto> getAll(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) Long animalId) {

        if (startDate != null || endDate != null || doctorId != null || animalId != null) {
        return appointmentService.findAllWithFilters(startDate, endDate, doctorId, animalId);
        } else {
            return appointmentService.findAll();
        }
    }

    @PutMapping("/{id}")
    public AppointmentResponseDto update(@PathVariable long id, @Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        return appointmentService.update(id, appointmentRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        appointmentService.deleteById(id);
    }
}
