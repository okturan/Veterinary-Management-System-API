package dev.patika.veterinary.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import dev.patika.veterinary.entities.dtos.request.AppointmentRequestDto;
import dev.patika.veterinary.entities.dtos.response.AppointmentResponseDto;
import dev.patika.veterinary.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

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
            @RequestParam(required = false) Long animalId)
    {

        if (startDate != null || endDate != null || doctorId != null || animalId != null) {
            return appointmentService.findAllWithFilters(startDate, endDate, doctorId, animalId);
        } else {
            return appointmentService.findAll();
        }
    }

    @PutMapping("/{id}")
    public AppointmentResponseDto update(@PathVariable long id,
                                         @Valid @RequestBody AppointmentRequestDto appointmentRequestDto)
    {
        return appointmentService.update(id, appointmentRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        appointmentService.deleteById(id);
    }
}
