package com.accenture.repository.entity;

import com.accenture.model.Permis;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("CLIENT")

public class Client extends UtilisateurConnecte {


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;
    private LocalDate dateDeNaissance;
    private LocalDate dateInscription;
    private List<Permis> permis;
    private Boolean desactive;


}
