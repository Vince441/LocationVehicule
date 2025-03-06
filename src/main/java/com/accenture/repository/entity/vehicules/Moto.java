package com.accenture.repository.entity.vehicules;


import com.accenture.model.vehicule.Transmission;
import com.accenture.model.vehicule.TypeMoto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("MOTO")
@EqualsAndHashCode(callSuper = true)


public class Moto extends Vehicule {


    private Integer nombreDeCylindre;
    private Boolean cylindree;
    private Integer puissanceKw;
    private Integer hauteurDeSelle;
    private Transmission transmission;
    private TypeMoto typeMoto;

}
