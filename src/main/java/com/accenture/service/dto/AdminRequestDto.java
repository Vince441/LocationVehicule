package com.accenture.service.dto;

import com.accenture.model.Fonction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminRequestDto(
        @NotBlank(message = "Le nom est obligatoire")
        String nom,

        @NotBlank(message = "Le prenom est obligatoire")
        String prenom,

        @NotBlank(message = "L'email est obligatoire")
        String email,

        @NotBlank(message = "Le password est obligatoire")
        String password,

        @NotNull(message = "La fonction est obligatoire")
        Fonction fonction) {
}
