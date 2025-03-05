package com.accenture.service.dto.vehicules;

import com.accenture.model.Permis;
import com.accenture.model.vehicule.Transmission;
import com.accenture.model.vehicule.TypeCarburant;
import com.accenture.model.vehicule.TypeVoiture;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Détails du véhicule")
public record VoitureResponseDto(

        int id,
        @Schema(description = "Marque de la voiture", example = "Marque")
        String marque,

        @Schema(description = "Model de la voiture", example = "Model")
        String modele,

        @Schema(description = "Couleur de la voiture", example = "Couleur")
        String couleur,

        @Schema(description = "Type de permis", example = "A")
        Permis permis,

        @Schema(description = "Nombre de place", example = "5")
        int nombrePlace,

        @Schema(description = "Type de Carburant")
        TypeCarburant carburant,

        @Schema(description = "Nombre de porte", example = "5")
        int nombreDePorte,


        @Schema(description = "Type de transmission", example = "Automatique")
        Transmission transmission,

        @Schema(description = "Climatisation", example = "True")
        Boolean climatisation,

        @Schema(description = "Nombre de bagage", example = "5")
        int nombreDeBagage,

        @Schema(description = "Tarif Journalier", example = "150")
        int tarifJournalier,


        @Schema(description = "Kilometrage", example = "50 000")
        int kilometrage,

        @Schema(description = "Actif / Non Actif", example = "True")
        Boolean actif,

        @Schema(description = "Retirer du parc", example = "False")
        Boolean retirerDuParc,

        @Schema(description = "Type de Moto")
        @NotBlank(message = "Le type de moto est obligatoire")
        TypeVoiture typeVoiture

) {
}
