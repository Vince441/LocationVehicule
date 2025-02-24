package com.accenture.service.dto;


import com.accenture.model.Permis;
import com.accenture.repository.entity.Adresse;

import java.time.LocalDate;
import java.util.List;

public record ClientResponseDto(
        String nom,
        String prenom,
        String email,
        String password,
        Adresse adresse,
        LocalDate dateDeNaissance,
        List<Permis> permis,
        Boolean desactive


) {
}
