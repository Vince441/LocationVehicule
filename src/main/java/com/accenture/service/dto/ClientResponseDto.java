package com.accenture.service.dto;


import com.accenture.repository.entity.Adresse;

import java.time.LocalDate;

public record ClientResponseDto(
        Long id,
        String nom,
        String prenom,
        String email,
        String password,
        Adresse adresse,
        LocalDate dateDeNaissance,
        LocalDate dateInscription,
        String permis,
        Boolean desactive


) {
}
