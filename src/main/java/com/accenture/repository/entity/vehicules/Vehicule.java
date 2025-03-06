package com.accenture.repository.entity.vehicules;

import com.accenture.model.Permis;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_vehicule")
@Table(name = "Vehicule")

public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String marque;
    private String modele;
    private String couleur;
    private Permis permis;
    private Integer poid;
    private Integer tarifJournalier;
    private Integer kilometrage;
    private Boolean actif;
    private Boolean retirerDuParc;

}
