package com.accenture.service.dto.vehicules;

import com.accenture.model.Permis;
import com.accenture.model.vehicule.Transmission;
import com.accenture.model.vehicule.TypeMoto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MotoRequestDto(

        @Schema(description = "Marque de la voiture", example = "Marque")
        @NotBlank(message = "La marque est obligatoire")
        String marque,

        @Schema(description = "Model de la voiture", example = "Model")
        @NotBlank(message = "Le modele est obligatoire")
        String modele,

        @Schema(description = "Couleur de la voiture", example = "Couleur")
        @NotBlank(message = "La couleur est obligatoire")
        String couleur,

        @Schema(description = "Type de permis", example = "A1")
        @NotNull(message = "Le permis est obligatoire")
        Permis permis,

        @Schema(description = "Poid de la moto", example = "250")
        @NotNull(message = "Le poid est obligatoire")
        Integer poid,

        @Schema(description = "Tarif Journalier", example = "150")
        @NotNull(message = "Le tarif journalier est obligatoire")
        Integer tarifJournalier,

        @Schema(description = "Kilometrage", example = "50 000")
        @NotNull(message = "Le kilometrage est obligatoire")
        Integer kilometrage,

        @Schema(description = "Actif / Non Actif", example = "True")
        @NotNull(message = "Le champ est obligatoire")
        Boolean actif,

        @Schema(description = "Retirer du parc", example = "False")
        @NotNull(message = "Le champ est obligatoire")
        Boolean retirerDuParc,

        @Schema(description = "Nombre de cylindre", example = "4")
        @NotNull(message = "Le champ est obligatoire")
        Integer nombreDeCylindre,

        @Schema(description = "Cylindre / Pas de Cylindre", example = "True")
        @NotNull(message = "Le champ est obligatoire")
        Boolean cylindree,

        @Schema(description = "Puissance en Kw", example = "73")
        @NotNull(message = "Le champ est obligatoire")
        Integer puissanceKw,

        @Schema(description = "Puissance en Kw", example = "1")
        @NotNull(message = "Le champ est obligatoire")
        Integer hauteurDeSelle,

        @Schema(description = "Type de transmission")
        @NotBlank(message = "Le type de transmission est obligatoire")
        Transmission transmission,

        @Schema(description = "Type de Moto")
        @NotBlank(message = "Le type de moto est obligatoire")
        TypeMoto typeMoto

) {


}
