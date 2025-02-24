package com.accenture.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_connected")
@Table(name = "utilisateur_connecte")

public class UtilisateurConnecte {

    @Id
    private String email;
    private String nom;
    private String prenom;
    private String password;

}
