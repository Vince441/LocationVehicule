package com.accenture.repository.entity.vehicules;


import com.accenture.model.vehicule.Transmission;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("MOTO")

public class Moto extends Vehicule {


    private int nombreDeCylindre;
    private boolean cylindree;
    private int puissanceKw;
    private int hauteurDeSelle;
    private Transmission transmission;


}
