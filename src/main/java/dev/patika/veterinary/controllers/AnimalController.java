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

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.dtos.request.AnimalRequestDto;
import dev.patika.veterinary.entities.dtos.request.VaccinationRequestDto;
import dev.patika.veterinary.entities.dtos.response.AnimalResponseDto;
import dev.patika.veterinary.entities.dtos.response.VaccinationResponseDto;
import dev.patika.veterinary.services.AnimalService;
import dev.patika.veterinary.services.VaccinationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final VaccinationService vaccinationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponseDto create(@Valid @RequestBody Animal animal) {
        return animalService.save(animal);
    }

    @PostMapping("/{id}/vaccinations")
    @ResponseStatus(HttpStatus.CREATED)
    public VaccinationResponseDto createVaccinationForAnimal(@PathVariable long id,
                                                  @Valid @RequestBody VaccinationRequestDto vaccinationRequestDto)
    {
        return vaccinationService.saveVaccinationForAnimal(id, vaccinationRequestDto);
    }

    @GetMapping("/{id}")
    public AnimalResponseDto getById(@PathVariable long id) {
        return animalService.findById(id);
    }

    // /api/animals?name={name}
    @GetMapping
    public List<AnimalResponseDto> getAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return animalService.findByName(name);
        }
        return animalService.findAll();
    }

    @GetMapping("/{id}/vaccinations")
    public List<VaccinationResponseDto> getByAnimalId(@PathVariable long id) {
        return vaccinationService.findByAnimalId(id);
    }

    @PutMapping("/{id}")
    public AnimalResponseDto update(@PathVariable long id, @Valid @RequestBody AnimalRequestDto animalRequestDto) {
        return animalService.update(id, animalRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        animalService.deleteById(id);
    }
}
