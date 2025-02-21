package com.accenture.service.dto;

import com.accenture.model.Fonction;

public record AdminRequestDto(
        String nom,
        String prenom,
        String email,
        String password,
        Fonction fonction
) {
}
