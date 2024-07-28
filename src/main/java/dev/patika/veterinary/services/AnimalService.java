package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.util.List;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Owner;
import dev.patika.veterinary.entities.dtos.mappers.AnimalMapper;
import dev.patika.veterinary.entities.dtos.request.AnimalRequestDto;
import dev.patika.veterinary.entities.dtos.response.AnimalResponseDto;
import dev.patika.veterinary.repositories.AnimalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    public AnimalResponseDto save(Animal animal) {
        Animal savedAnimal = animalRepository.save(animal);
        return animalMapper.animalToAnimalResponseDto(savedAnimal);
    }

    public AnimalResponseDto createAndAssignToOwner(Animal animal, Owner owner) {
        animal.setOwner(owner);
        Animal savedAnimal = animalRepository.save(animal);
        return animalMapper.animalToAnimalResponseDto(savedAnimal);
    }

    public AnimalResponseDto findById(long id) {
        Animal animal = animalRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Animal not found with id: " + id));
        return animalMapper.animalToAnimalResponseDto(animal);
    }

    public List<AnimalResponseDto> findAll() {
        return animalRepository.findAll()
                               .stream()
                               .map(animalMapper::animalToAnimalResponseDto)
                               .toList();
    }

    public List<AnimalResponseDto> findByName(String name) {
        return animalRepository.findByNameContainsIgnoreCase(name)
                               .stream()
                               .map(animalMapper::animalToAnimalResponseDto)
                               .toList();
    }

    public AnimalResponseDto update(long id, AnimalRequestDto animalRequestDto) {
        Animal animal = animalRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Animal not found with id: " + id));
        animalMapper.updateAnimalFromDto(animalRequestDto, animal);
        return animalMapper.animalToAnimalResponseDto(animalRepository.save(animal));
    }

    public void deleteById(long id) {
        animalRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Animal not found with id: " + id));
        animalRepository.deleteById(id);
    }

}
