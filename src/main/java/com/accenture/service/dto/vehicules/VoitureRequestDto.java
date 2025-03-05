package com.accenture.service.dto.vehicules;

import com.accenture.model.Permis;
import com.accenture.model.vehicule.Transmission;
import com.accenture.model.vehicule.TypeCarburant;
import com.accenture.model.vehicule.TypeVoiture;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Détails des véhicules")

public record VoitureRequestDto(

        @Schema(description = "Marque de la voiture", example = "Marque")
        @NotBlank(message = "La marque est obligatoire")
        String marque,

        @Schema(description = "Model de la voiture", example = "Model")
        @NotBlank(message = "Le modele est obligatoire")
        String modele,

        @Schema(description = "Couleur de la voiture", example = "Couleur")
        @NotBlank(message = "La couleur est obligatoire")
        String couleur,

        @Schema(description = "Type de permis", example = "A")
        @NotNull(message = "Le permis est obligatoire")
        Permis permis,

        @Schema(description = "Nombre de place", example = "5")
        @NotNull(message = "Le nombre de place est obligatoire")
        Integer nombrePlace,

        @Schema(description = "Type de Carburant")
        @NotBlank(message = "Le type de carburant est obligatoire")
        TypeCarburant carburant,

        @Schema(description = "Nombre de porte", example = "5")
        @NotNull(message = "Le nombre de porte est obligatoire")
        Integer nombreDePorte,

        @Schema(description = "Type de transmission")
        @NotBlank(message = "Le type de transmission est obligatoire")
        Transmission transmission,

        @Schema(description = "Climatisation", example = "True")
        @NotNull(message = "Le champ est obligatoire")
        Boolean climatisation,

        @Schema(description = "Nombre de bagage", example = "5")
        @NotNull(message = "Le nombre de bagage est obligatoire")
        Integer nombreDeBagage,

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

        @Schema(description = "Type de Voiture")
        @NotBlank(message = "Le type de voiture est obligatoire")
        TypeVoiture typeVoiture

) {
}
