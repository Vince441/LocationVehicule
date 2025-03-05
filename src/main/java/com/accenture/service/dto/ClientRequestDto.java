package com.accenture.service.dto;

import com.accenture.model.Permis;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Détails des clients")
public record ClientRequestDto(

        @Schema(description = "Nom", example = "Louv")
        @NotBlank(message = "Le nom est obligatoire")
        String nom,

        @Schema(description = "Prenom", example = "V")
        @NotBlank(message = "Le prenom est obligatoire")
        String prenom,

        @Schema(description = "Email", example = "v.l@gmail.com")
        @NotBlank(message = "L'email est obligatoire")
        String email,

        @Schema(description = "Password", example = "azertyuiop")
        @NotBlank(message = "Le password est obligatoire")
        String password,

        @NotBlank(message = "L'adresse est obligatoire")
        AdresseDto adresse,

        @Schema(description = "Date de Naissance", example = "1990-05-25")
        @NotNull(message = "La date de naissance est obligatoire")
        LocalDate dateDeNaissance,

        @Schema(description = "Permis")
        @NotNull(message = "Le permis est obligatoire")
        List<Permis> permis,

        @Schema(description = "Activer / Desactiver", example = "true")
        Boolean desactive


) {


}
