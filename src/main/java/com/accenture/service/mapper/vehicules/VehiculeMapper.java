package com.accenture.service.mapper.vehicules;

import com.accenture.repository.entity.vehicules.Moto;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.vehicules.MotoResponseDto;
import com.accenture.service.dto.vehicules.VoitureResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {


    VoitureResponseDto toVoitureResponseDto(Voiture voiture);

    MotoResponseDto toMotoResponseDto(Moto moto);


}
