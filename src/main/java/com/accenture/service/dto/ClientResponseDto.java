package com.accenture.service.dto;

import com.accenture.repository.entity.Adresse;


import java.time.LocalDate;

public record ClientResponseDto(
//        Long id,
        String nom,
        String prenom,
        Adresse adresse,
        String email,
        String password,
        LocalDate dateDeNaissance,
        LocalDate dateInscription,
        String permis,
        Boolean desactive


) {
}
