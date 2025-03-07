package com.accenture.service.dto;

import com.accenture.model.Fonction;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdminResponseDto(

        @Schema(description = "Nom", example = "Louv")
        String nom,

        @Schema(description = "Prenom", example = "V")
        String prenom,

        @Schema(description = "Email", example = "v.l@gmail.com")
        String email,



        Fonction fonction
) {
}
