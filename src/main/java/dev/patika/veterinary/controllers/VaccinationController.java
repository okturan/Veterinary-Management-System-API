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

import dev.patika.veterinary.entities.Vaccination;
import dev.patika.veterinary.entities.dtos.request.VaccinationRequestDto;
import dev.patika.veterinary.entities.dtos.response.VaccinationResponseDto;
import dev.patika.veterinary.services.VaccinationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vaccinations")
@RequiredArgsConstructor
public class VaccinationController {

    private final VaccinationService vaccinationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VaccinationResponseDto create(@Valid @RequestBody Vaccination vaccination) {
        return vaccinationService.save(vaccination);
    }

    @GetMapping("/{id}")
    public VaccinationResponseDto getById(@PathVariable long id) {
        return vaccinationService.findById(id);
    }

    @GetMapping
    public List<VaccinationResponseDto> getAll() {
        return vaccinationService.findAll();
    }

    @PutMapping("/{id}")
    public VaccinationResponseDto update(@PathVariable long id,
                                         @Valid @RequestBody VaccinationRequestDto vaccinationRequestDto)
    {
        return vaccinationService.update(id, vaccinationRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        vaccinationService.deleteById(id);
    }
}
