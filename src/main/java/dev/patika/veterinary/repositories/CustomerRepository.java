package dev.patika.veterinary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import dev.patika.veterinary.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContainsIgnoreCase(String name);
}