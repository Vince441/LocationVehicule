package com.accenture.service.vehicules;

import com.accenture.service.dto.vehicules.VoitureRequestDto;
import com.accenture.service.dto.vehicules.VoitureResponseDto;

import java.util.List;

public interface VoitureService {

    List<VoitureResponseDto> trouverToute();

    VoitureResponseDto ajouter(VoitureRequestDto voitureRequestDto);

    List<VoitureResponseDto> trouverActif(Boolean actif);


    VoitureResponseDto trouverUneVoiture(int id);



}
