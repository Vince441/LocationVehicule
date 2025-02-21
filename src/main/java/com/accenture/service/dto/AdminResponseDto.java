package com.accenture.service.dto;

import com.accenture.model.Fonction;

public record AdminResponseDto(
        Long id,
        String nom,
        String prenom,
        String email,
        String password,
        Fonction fonction
) {
}
