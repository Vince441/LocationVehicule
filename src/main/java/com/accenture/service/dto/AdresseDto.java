package com.accenture.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Détails des adresses")
public record AdresseDto(

        @Schema(description = "Rue", example = "1_Rue De La Paix")
        @NotBlank(message = "La rue est obligatoire")
        String rue,

        @Schema(description = "Code Postal", example = "39000")
        @NotBlank(message = "Le code postal est obligatoire")
        String codePostal,

        @Schema(description = "Ville", example = "Paris")
        @NotBlank(message = "La ville est obligatoire")
        String ville


) {
}
