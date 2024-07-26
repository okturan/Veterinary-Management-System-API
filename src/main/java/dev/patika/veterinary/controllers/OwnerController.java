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
import dev.patika.veterinary.entities.Owner;
import dev.patika.veterinary.entities.dtos.request.OwnerRequestDto;
import dev.patika.veterinary.entities.dtos.response.AnimalResponseDto;
import dev.patika.veterinary.entities.dtos.response.OwnerResponseDto;
import dev.patika.veterinary.services.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerResponseDto create(@Valid @RequestBody Owner owner) {
        return ownerService.save(owner);
    }

    @PostMapping("/{id}/animals")
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponseDto addAnimalToOwner(@PathVariable long id, @Valid @RequestBody Animal animal) {
        return ownerService.addAnimalToOwner(id, animal);
    }

    @GetMapping("/{id}")
    public OwnerResponseDto getById(@PathVariable long id) {
        return ownerService.findById(id);
    }

    @GetMapping
    public List<OwnerResponseDto> getAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return ownerService.findByName(name);
        }
        return ownerService.findAll();
    }

    @PutMapping("/{id}")
    public OwnerResponseDto update(@PathVariable long id,
                                   @Valid @RequestBody OwnerRequestDto ownerRequestDto)
    {
        return ownerService.update(id, ownerRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        ownerService.deleteById(id);
    }
}
