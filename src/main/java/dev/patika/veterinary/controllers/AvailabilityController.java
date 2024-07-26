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

import java.util.List;

import dev.patika.veterinary.entities.Availability;
import dev.patika.veterinary.entities.dtos.request.AvailabilityRequestDto;
import dev.patika.veterinary.entities.dtos.response.AvailabilityResponseDto;
import dev.patika.veterinary.services.AvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/availabilities")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvailabilityResponseDto create(@Valid @RequestBody Availability availability) {
        return availabilityService.save(availability);
    }

    @GetMapping("/{id}")
    public AvailabilityResponseDto getById(@PathVariable long id) {
        return availabilityService.findById(id);
    }

    @GetMapping
    public List<AvailabilityResponseDto> getAll(@RequestParam(required = false) Long doctorId) {
        if (doctorId != null) {
            return availabilityService.findByDoctor(doctorId);
        }
        return availabilityService.findAll();
    }

    @PutMapping("/{id}")
    public AvailabilityResponseDto update(@PathVariable long id,
                                          @Valid @RequestBody AvailabilityRequestDto availabilityRequestDto)
    {
        return availabilityService.update(id, availabilityRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        availabilityService.deleteById(id);
    }
}
