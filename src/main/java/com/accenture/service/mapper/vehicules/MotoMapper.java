package com.accenture.service.mapper.vehicules;

import com.accenture.repository.entity.vehicules.Moto;

import com.accenture.service.dto.vehicules.MotoRequestDto;
import com.accenture.service.dto.vehicules.MotoResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MotoMapper {


    Moto toMoto(MotoRequestDto motoRequestDto);

    MotoResponseDto toMotoResponseDto(Moto moto);


}
