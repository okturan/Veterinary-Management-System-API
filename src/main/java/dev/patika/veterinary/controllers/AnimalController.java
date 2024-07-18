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

import dev.patika.veterinary.dtos.request.AnimalRequestDto;
import dev.patika.veterinary.dtos.response.AnimalResponseDto;
import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.services.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponseDto create(@Valid @RequestBody Animal animal) {
        return animalService.save(animal);
    }

    @GetMapping("/{id}")
    public AnimalResponseDto getById(@PathVariable long id) {
        return animalService.findById(id);
    }

    @GetMapping
    public List<AnimalResponseDto> getAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return animalService.findByName(name);
        }
        return animalService.findAll();
    }

    @PutMapping("/{id}")
    public AnimalResponseDto update(@PathVariable long id, @Valid @RequestBody AnimalRequestDto animal) {
        return animalService.update(id, animal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        animalService.deleteById(id);
    }
}
