package com.accenture.service.dto;

import java.time.LocalDate;

public record ClientRequestDto(

        String nom,
        String prenom,
        String email,
        String password,
        AdresseDto adresse,
        LocalDate dateDeNaissance,
        LocalDate dateInscription,
        String permis,
        Boolean desactive

) {


}
