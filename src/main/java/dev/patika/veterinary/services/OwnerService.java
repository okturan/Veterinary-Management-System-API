package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.util.List;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Owner;
import dev.patika.veterinary.entities.dtos.mappers.OwnerMapper;
import dev.patika.veterinary.entities.dtos.request.OwnerRequestDto;
import dev.patika.veterinary.entities.dtos.response.AnimalResponseDto;
import dev.patika.veterinary.entities.dtos.response.OwnerResponseDto;
import dev.patika.veterinary.repositories.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    private final AnimalService animalService;

    public OwnerResponseDto save(Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapper.ownerToOwnerResponseDto(savedOwner);
    }

    public AnimalResponseDto addAnimalToOwner(long ownerId, Animal animal) {
        Owner owner = ownerRepository.findById(ownerId)
                                     .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + ownerId));
        return animalService.createAndAssignToOwner(animal, owner);
    }

    public OwnerResponseDto findById(long id) {
        Owner owner = ownerRepository.findById(id)
                                     .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + id));
        return ownerMapper.ownerToOwnerResponseDto(owner);
    }

    public List<OwnerResponseDto> findAll() {
        return ownerRepository.findAll()
                              .stream()
                              .map(ownerMapper::ownerToOwnerResponseDto)
                              .toList();
    }

    public List<OwnerResponseDto> findByName(String name) {
        return ownerRepository.findByNameContainsIgnoreCase(name)
                              .stream()
                              .map(ownerMapper::ownerToOwnerResponseDto)
                              .toList();
    }

    public OwnerResponseDto update(long id, OwnerRequestDto ownerRequestDto) {
        Owner owner = ownerRepository.findById(id)
                                     .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + id));
        Owner mergedOwner = ownerMapper.updateOwnerFromDto(ownerRequestDto, owner);
        return ownerMapper.ownerToOwnerResponseDto(ownerRepository.save(mergedOwner));
    }

    public void deleteById(long id) {
        ownerRepository.findById(id)
                       .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + id));
        ownerRepository.deleteById(id);
    }
}
