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

import dev.patika.veterinary.dtos.request.CustomerRequestDto;
import dev.patika.veterinary.dtos.response.CustomerResponseDto;
import dev.patika.veterinary.entities.Customer;
import dev.patika.veterinary.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto create(@Valid @RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @GetMapping("/{id}")
    public CustomerResponseDto getById(@PathVariable long id) {
        return customerService.findById(id);
    }

    @GetMapping
    public List<CustomerResponseDto> getAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return customerService.findByName(name);
        }
        return customerService.findAll();
    }

    @PutMapping("/{id}")
    public CustomerResponseDto update(@PathVariable int id, @Valid @RequestBody CustomerRequestDto customer) {
        return customerService.update(id, customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        customerService.deleteById(id);
    }
}
