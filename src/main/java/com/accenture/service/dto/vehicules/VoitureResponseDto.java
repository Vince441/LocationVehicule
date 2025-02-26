package com.accenture.service.dto.vehicules;

import com.accenture.model.Permis;
import com.accenture.model.vehicule.Transmission;
import com.accenture.model.vehicule.TypeCarburant;


public record VoitureResponseDto(
        int id,
        String marque,
        String modele,
        String couleur,
        Permis permis,

        int nombrePlace,
        TypeCarburant carburant,
        int nombreDePorte,
        Transmission transmission,
        Boolean climatisation,
        int nombreDeBagage,

        int tarifJournalier,
        int kilometrage,
        Boolean actif,
        Boolean retirerDuParc
) {
}
