package com.accenture.service.mapper;

import com.accenture.repository.entity.Adresse;
import com.accenture.service.dto.AdresseDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdresseMapper {

    Adresse toAdresse(AdresseDto adresseDto);
    AdresseDto toAdresseDto(Adresse adresse);

}