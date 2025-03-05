package com.accenture.service.dto.locations;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;


public record LocationRequestDto(

        @Schema(description = "Date de debut", example = "2024-01-01")
        LocalDate dateDeDebut,

        @Schema(description = "Date de debut", example = "2026-01-01")
        LocalDate dateDeFin
) {
}
