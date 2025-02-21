package com.accenture.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClientRequestDto(
        @NotBlank(message = "Le nom est obligatoire")
        String nom,

        @NotBlank(message = "Le prenom est obligatoire")
        String prenom,

        @NotBlank(message = "L'email est obligatoire")
        String email,

        @NotBlank(message = "Le password est obligatoire")
        String password,

        @NotBlank(message = "L'adresse est obligatoire")
        AdresseDto adresse,

        @NotNull(message = "La date de naissance est obligatoire")
        LocalDate dateDeNaissance,

        @NotNull(message = "La date d'inscription est obligatoire")
        LocalDate dateInscription,

        String permis,
        Boolean desactive

) {


}
