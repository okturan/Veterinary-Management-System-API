package dev.patika.veterinary.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import dev.patika.veterinary.dtos.request.CustomerRequestDto;
import dev.patika.veterinary.dtos.response.CustomerResponseDto;
import dev.patika.veterinary.entities.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResponseDto customerToCustomerResponseDto(Customer customer);

    Customer updateCustomerFromDto(CustomerRequestDto customerRequestDto, @MappingTarget Customer customer);

}
