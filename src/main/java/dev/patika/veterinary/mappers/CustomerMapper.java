package dev.patika.veterinary.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import dev.patika.veterinary.entities.Customer;
import dev.patika.veterinary.entities.dtos.request.CustomerRequestDto;
import dev.patika.veterinary.entities.dtos.response.CustomerResponseDto;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "animals", target = "animals")
    CustomerResponseDto customerToCustomerResponseDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "animals", ignore = true)
    Customer updateCustomerFromDto(CustomerRequestDto customerRequestDto, @MappingTarget Customer customer);

}
