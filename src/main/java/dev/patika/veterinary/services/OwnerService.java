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
public class OwnerService implements IService<Owner, OwnerResponseDto, OwnerRequestDto> {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    private final AnimalService animalService;

    @Override
    public OwnerResponseDto save(Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapper.ownerToOwnerResponseDto(savedOwner);
    }

    public AnimalResponseDto addAnimalToOwner(long ownerId, Animal animal) {
        Owner owner = ownerRepository.findById(ownerId)
                                     .orElseThrow(EntityNotFoundException::new);
        return animalService.createAndAssignToOwner(animal, owner);
    }

    @Override
    public OwnerResponseDto findById(long id) {
        Owner owner = ownerRepository.findById(id)
                                     .orElseThrow(EntityNotFoundException::new);
        return ownerMapper.ownerToOwnerResponseDto(owner);
    }

    @Override
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

    @Override
    public OwnerResponseDto update(long id, OwnerRequestDto ownerRequestDto) {
        Owner owner = ownerRepository.findById(id)
                                     .orElseThrow(EntityNotFoundException::new);
        Owner mergedOwner = ownerMapper.updateOwnerFromDto(ownerRequestDto, owner);
        return ownerMapper.ownerToOwnerResponseDto(ownerRepository.save(mergedOwner));
    }

    @Override
    public void deleteById(long id) {
        ownerRepository.findById(id)
                       .orElseThrow(EntityNotFoundException::new);
        ownerRepository.deleteById(id);
    }
}
