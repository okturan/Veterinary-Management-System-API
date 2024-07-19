package dev.patika.veterinary.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Customer;
import dev.patika.veterinary.entities.dtos.request.CustomerRequestDto;
import dev.patika.veterinary.entities.dtos.response.AnimalResponseDto;
import dev.patika.veterinary.entities.dtos.response.CustomerResponseDto;
import dev.patika.veterinary.mappers.CustomerMapper;
import dev.patika.veterinary.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService implements IService<Customer, CustomerResponseDto, CustomerRequestDto> {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AnimalService animalService;

    @Override
    public CustomerResponseDto save(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerResponseDto(savedCustomer);
    }

    public AnimalResponseDto addAnimalToCustomer(long customerId, Animal animal) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with id: " + customerId);
        }
        return animalService.createAndAssignToCustomer(animal, optionalCustomer.get());
    }

    @Override
    public CustomerResponseDto findById(long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        return customerMapper.customerToCustomerResponseDto(optionalCustomer.get());
    }

    @Override
    public List<CustomerResponseDto> findAll() {
        return customerRepository.findAll()
                                 .stream()
                                 .map(customerMapper::customerToCustomerResponseDto)
                                 .toList();
    }

    public List<CustomerResponseDto> findByName(String name) {
        return customerRepository.findByNameContainsIgnoreCase(name)
                                 .stream()
                                 .map(customerMapper::customerToCustomerResponseDto)
                                 .toList();
    }

    @Override
    public CustomerResponseDto update(long id, CustomerRequestDto customerRequestDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        Customer mergedCustomer = customerMapper.updateCustomerFromDto(customerRequestDto, optionalCustomer.get());
        return customerMapper.customerToCustomerResponseDto(customerRepository.save(mergedCustomer));
    }

    @Override
    public void deleteById(long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
