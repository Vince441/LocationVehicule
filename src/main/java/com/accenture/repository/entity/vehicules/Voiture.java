package com.accenture.repository.entity.vehicules;

import com.accenture.model.vehicule.Transmission;
import com.accenture.model.vehicule.TypeCarburant;
import com.accenture.model.vehicule.TypeVoiture;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("VOITURE")
@EqualsAndHashCode(callSuper = true)

public class Voiture extends Vehicule {


    private Integer nombrePlace;
    private TypeCarburant carburant;
    private Integer nombreDePorte;
    private Transmission transmission;
    private Boolean climatisation;
    private Integer nombreDeBagage;
    private TypeVoiture typeVoiture;

}
