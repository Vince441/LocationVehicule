package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.UtilisateurConnecteRequestDto;
import com.accenture.service.dto.UtilisateurConnecteResponseDto;


import java.util.List;

public interface UtilisateurConnecteService {

    UtilisateurConnecteResponseDto ajouter(UtilisateurConnecteRequestDto UtilisateurConnecteRequestDto);

    List<UtilisateurConnecteResponseDto> liste();

    UtilisateurConnecteResponseDto trouver(Long id) throws ClientException;



}
