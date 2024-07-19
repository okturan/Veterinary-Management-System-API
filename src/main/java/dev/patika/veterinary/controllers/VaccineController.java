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

import dev.patika.veterinary.entities.Vaccine;
import dev.patika.veterinary.entities.dtos.request.VaccineRequestDto;
import dev.patika.veterinary.entities.dtos.response.VaccineResponseDto;
import dev.patika.veterinary.services.VaccineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vaccines")
@RequiredArgsConstructor
public class VaccineController {

    private final VaccineService vaccineService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VaccineResponseDto create(@Valid @RequestBody Vaccine vaccine) {
        return vaccineService.save(vaccine);
    }

    @GetMapping("/{id}")
    public VaccineResponseDto getById(@PathVariable long id) {
        return vaccineService.findById(id);
    }

    @GetMapping
    public List<VaccineResponseDto> getAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return vaccineService.findByName(name);
        }
        return vaccineService.findAll();
    }

    @PutMapping("/{id}")
    public VaccineResponseDto update(@PathVariable long id, @Valid @RequestBody VaccineRequestDto vaccineRequestDto) {
        return vaccineService.update(id, vaccineRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        vaccineService.deleteById(id);
    }
}
