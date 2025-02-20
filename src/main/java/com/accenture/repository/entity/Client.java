package com.accenture.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("CLIENT")
public class Client extends UtilisateurConnecte {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;
    private LocalDate dateDeNaissance;
    private LocalDate dateInscription;
    private String permis;
    private Boolean desactive;


}
