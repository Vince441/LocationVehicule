package com.accenture.service.mapper.vehicules;


import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.vehicules.VoitureRequestDto;
import com.accenture.service.dto.vehicules.VoitureResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoitureMapper {

    Voiture toVoiture(VoitureRequestDto voitureRequestDto);

    VoitureResponseDto toVoitureResponseDto(Voiture voiture);


}
