package com.accenture.service.dto;

import com.accenture.model.Fonction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminRequestDto(

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

        @NotNull(message = "La fonction est obligatoire")
        Fonction fonction) {

}
