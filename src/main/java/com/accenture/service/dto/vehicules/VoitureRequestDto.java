package com.accenture.service.dto.vehicules;

import com.accenture.model.Permis;
import com.accenture.model.vehicule.Transmission;
import com.accenture.model.vehicule.TypeCarburant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VoitureRequestDto(

        @NotBlank(message = "La marque est obligatoire")
        String marque,

        @NotBlank(message = "Le modele est obligatoire")
        String modele,

        @NotBlank(message = "La couleur est obligatoire")
        String couleur,

        @NotNull(message = "Le permis est obligatoire")
        Permis permis,


        @NotNull(message = "Le nombre de place est obligatoire")
        Integer nombrePlace,

        @NotBlank(message = "Le type de carburant est obligatoire")
        TypeCarburant carburant,

        @NotNull(message = "Le nombre de porte est obligatoire")
        Integer nombreDePorte,

        @NotBlank(message = "Le type de transmission est obligatoire")
        Transmission transmission,

        @NotNull(message = "Le champ est obligatoire")
        Boolean climatisation,

        @NotNull(message = "Le nombre de bagage est obligatoire")
        Integer nombreDeBagage,

        @NotNull(message = "Le tarif journalier est obligatoire" )
        int tarifJournalier,

        @NotNull (message = "Le kilometrage est obligatoire")
        int kilometrage,

        @NotNull(message = "Le champ est obligatoire")
        Boolean actif,

        @NotNull(message = "Le champ est obligatoire")
        Boolean retirerDuParc

) {
}
