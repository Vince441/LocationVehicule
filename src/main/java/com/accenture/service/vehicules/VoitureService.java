package com.accenture.service.vehicules;

import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.service.dto.vehicules.VoitureRequestDto;
import com.accenture.service.dto.vehicules.VoitureResponseDto;

import java.util.List;

public interface VoitureService {

    List<VoitureResponseDto> trouverToute() throws VehiculeException;

    VoitureResponseDto ajouter(VoitureRequestDto voitureRequestDto) throws VehiculeException;

    List<VoitureResponseDto> trouverActif(Boolean actif) throws VehiculeException;


    VoitureResponseDto trouverUneVoiture(int id) throws VehiculeException;

    VoitureResponseDto modifierPartiellement(int id, VoitureRequestDto voitureRequestDto) throws VehiculeException;

    void supprimer(int id) throws VehiculeException;


}
