package dev.patika.veterinary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import dev.patika.veterinary.entities.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByNameContainsIgnoreCase(String name);
}