package com.accenture.service.dto;

import com.accenture.model.Permis;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;


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

        @NotNull(message = "Le permis est obligatoire")
        List<Permis> permis,


        Boolean desactive



) {


}
