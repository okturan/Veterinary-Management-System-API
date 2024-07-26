package dev.patika.veterinary.entities.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import dev.patika.veterinary.entities.Owner;
import dev.patika.veterinary.entities.dtos.request.OwnerRequestDto;
import dev.patika.veterinary.entities.dtos.response.OwnerResponseDto;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);

    @Mapping(source = "animals", target = "animals")
    OwnerResponseDto ownerToOwnerResponseDto(Owner owner);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "animals", ignore = true)
    Owner updateOwnerFromDto(OwnerRequestDto ownerRequestDto, @MappingTarget Owner owner);

}
