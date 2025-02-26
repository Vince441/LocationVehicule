package com.accenture.repository.entity.vehicules;

import com.accenture.model.vehicule.Transmission;
import com.accenture.model.vehicule.TypeCarburant;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("VOITURE")
@EqualsAndHashCode(callSuper = true)

public class Voiture extends Vehicule {


    private int nombrePlace;
    private TypeCarburant carburant;
    private int nombreDePorte;
    private Transmission transmission;
    private Boolean climatisation;
    private int nombreDeBagage;


}
