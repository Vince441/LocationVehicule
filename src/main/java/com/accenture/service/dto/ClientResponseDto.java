package com.accenture.service.dto;


import java.time.LocalDate;

public record ClientResponseDto(
        Long id,
        String nom,
        String prenom,
        String email,
        String password,
        AdresseDto adresse,
        LocalDate dateDeNaissance,
        LocalDate dateInscription,
        String permis,
        Boolean desactive


) {
}
