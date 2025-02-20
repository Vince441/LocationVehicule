package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.UtilisateurConnecteRequestDto;
import com.accenture.service.dto.UtilisateurConnecteResponseDto;

import java.util.List;

public class UtilisateurConnecteImpl implements UtilisateurConnecteService {
    @Override
    public UtilisateurConnecteResponseDto ajouter(UtilisateurConnecteRequestDto UtilisateurConnecteRequestDto) {
        return null;
    }

    @Override
    public List<UtilisateurConnecteResponseDto> liste() {
        return List.of();
    }


    @Override
    public UtilisateurConnecteResponseDto trouver(Long id) throws ClientException {
        return null;
    }



}
