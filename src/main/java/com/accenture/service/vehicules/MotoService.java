package com.accenture.service.vehicules;

import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.service.dto.vehicules.MotoRequestDto;
import com.accenture.service.dto.vehicules.MotoResponseDto;


import java.util.List;

public interface MotoService {


    List<MotoResponseDto> trouverActif(Boolean actif) throws VehiculeException;

    List<MotoResponseDto> trouverToute() throws VehiculeException;

    MotoResponseDto ajouter(MotoRequestDto motoRequestDto) throws VehiculeException;

    MotoResponseDto trouverUneMoto(int id) throws VehiculeException;

    MotoResponseDto modifierPartiellement(int id, MotoRequestDto motoRequestDto) throws VehiculeException;

    void supprimer(int id) throws VehiculeException;


}
