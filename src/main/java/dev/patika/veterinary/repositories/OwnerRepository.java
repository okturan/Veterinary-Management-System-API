package dev.patika.veterinary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import dev.patika.veterinary.entities.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findByNameContainsIgnoreCase(String name);
}