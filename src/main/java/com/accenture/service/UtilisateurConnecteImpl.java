package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.entity.UtilisateurConnecte;

import java.util.List;

public class UtilisateurConnecteImpl implements UtilisateurConnecteService {


    @Override
    public List<UtilisateurConnecte> liste() {
        return List.of();
    }


    @Override
    public UtilisateurConnecte trouver(Long id) throws ClientException {
        return null;
    }



}
