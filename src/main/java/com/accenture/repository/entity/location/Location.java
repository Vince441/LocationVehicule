package com.accenture.repository.entity.location;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.vehicules.Vehicule;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("LOCATION")
@EqualsAndHashCode(callSuper = false)

public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //    @ManyToOne
//    @JoinColumn(name = "client_id")
//    private Client client;
//    @ManyToOne
//    @JoinColumn(name = "vehicule_id")
//    private Vehicule vehicule;
    private LocalDate dateDeDebut;
    private LocalDate dateDeFin;

}
//
//        private Accessoires accessoires;
//    private int nombreDeKmParcourus;
//    private int montantTotal;
//    private LocalDate dateDeLaValidation;
//    private boolean etatLocation;
//
//}
