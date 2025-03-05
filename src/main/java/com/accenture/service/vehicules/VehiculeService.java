package com.accenture.service.vehicules;


import com.accenture.exception.vehicules.VehiculeException;

import java.util.List;

public interface VehiculeService {


    List<Record> trouverVehicules() throws VehiculeException;

    List<Record> tousActif(Boolean actif) throws VehiculeException;

    List<Record> retirerDuParc(Boolean retirerDuParc) throws VehiculeException;
}
