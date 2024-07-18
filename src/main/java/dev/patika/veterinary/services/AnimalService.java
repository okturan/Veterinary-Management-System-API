package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import dev.patika.veterinary.dtos.request.AnimalRequestDto;
import dev.patika.veterinary.dtos.response.AnimalResponseDto;
import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.mappers.AnimalMapper;
import dev.patika.veterinary.repositories.AnimalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalService implements IService<Animal, AnimalResponseDto, AnimalRequestDto> {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    @Override
    public AnimalResponseDto save(Animal entity) {
        Animal savedAnimal = animalRepository.save(entity);
        return animalMapper.animalToAnimalResponseDto(savedAnimal);
    }

    @Override
    public AnimalResponseDto findById(long id) {
        Optional<Animal> optionalAnimal = animalRepository.findById(id);
        if (optionalAnimal.isEmpty()) {
            throw new EntityNotFoundException("Animal not found with id: " + id);
        }
        return animalMapper.animalToAnimalResponseDto(optionalAnimal.get());
    }

    @Override
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

    @Override
    public AnimalResponseDto update(long id, AnimalRequestDto animalRequestDto) {
        Optional<Animal> optionalAnimal = animalRepository.findById(id);
        if (optionalAnimal.isEmpty()) {
            throw new EntityNotFoundException("Animal not found with id: " + id);
        }
        Animal mergedAnimal = animalMapper.updateAnimalFromDto(animalRequestDto, optionalAnimal.get());
        return animalMapper.animalToAnimalResponseDto(animalRepository.save(mergedAnimal));
    }

    @Override
    public void deleteById(long id) {
        if (!animalRepository.existsById(id)) {
            throw new EntityNotFoundException("Animal not found with id: " + id);
        }
        animalRepository.deleteById(id);
    }
}
