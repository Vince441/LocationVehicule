package com.accenture.service.dto;

import java.time.LocalDate;

public record ClientRequestDto(
//        Long id,
        String nom,
        String prenom,
        AdresseRequestDto adresse,
        String email,
        String password,
        LocalDate dateDeNaissance,
        LocalDate dateInscription,
        String permis,
        Boolean desactive

) {


}
