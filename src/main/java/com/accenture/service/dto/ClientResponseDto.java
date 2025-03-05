package com.accenture.service.dto;


import com.accenture.model.Permis;
import io.swagger.v3.oas.annotations.media.Schema;


import java.time.LocalDate;
import java.util.List;

public record ClientResponseDto(


        @Schema(description = "Nom", example = "Louv")
        String nom,

        @Schema(description = "Prenom", example = "V")
        String prenom,

        @Schema(description = "Email", example = "v.l@gmail.com")
        String email,

        @Schema(description = "Password", example = "azertyuiop")
        String password,

        AdresseDto adresse,

        @Schema(description = "Date de Naissance", example = "1990-05-25")
        LocalDate dateDeNaissance,

        @Schema(description = "Permis")
        List<Permis> permis,

        @Schema(description = "Activer / Desactiver", example = "true")
        Boolean desactive

) {
}
