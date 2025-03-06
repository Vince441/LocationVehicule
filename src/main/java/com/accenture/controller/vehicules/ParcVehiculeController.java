package com.accenture.controller.vehicules;

import com.accenture.exception.locations.LocationException;
import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.service.vehicules.VehiculeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parcVehicule")
public class ParcVehiculeController {


    private final VehiculeService vehiculeService;
    private static final Logger logger = LoggerFactory.getLogger(ParcVehiculeController.class);


    public ParcVehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @GetMapping
    @Operation(summary = "Liste des vehicules", description = "Permet de voir la liste de tout les véhicules.")
    @ApiResponse(responseCode = "201", description = "Liste des véhicules récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des véhicules non disponnible")
    List<Record> toutLesVehiculesDuParc() {
        try {
            List<Record> listeDeToutLesVehiculesDuParc = vehiculeService.trouverVehicules();
            logger.info("La liste de tout les véhicules récupérée avec succès");
            return listeDeToutLesVehiculesDuParc;
        } catch (VehiculeException e) {
            logger.error("Echec lors de la récupération de la liste de tout les véhicules");
            throw new LocationException(e.getMessage());
        }

    }

    @GetMapping("/rechercheActif")
    @Operation(summary = "Liste des vehicules actif et non actif", description = "Permet de voir la liste des voitures actif et non actif.")
    @ApiResponse(responseCode = "201", description = "Liste des vehicules actif / non actif récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des vehicules non disponnible")
    List<Record> tousLesVehiculesActifetNonActif(Boolean actif) {
        try {
            List<Record> listeDeToutlesVehiculesActifetNonActif = vehiculeService.tousActif(actif);
            logger.info("La liste des vehicules actif et non actif récupérée avec succès");
            return listeDeToutlesVehiculesActifetNonActif;
        } catch (VehiculeException e) {
            logger.error("Echec lors de la récupération de la liste des véhicules actif et non actif");
            throw new LocationException(e.getMessage());
        }
    }

    @GetMapping("/rechercheRetirerDuParc")
    @Operation(summary = "Liste des vehicules dans le Parc ou retiré du Parc ", description = "Permet de voir la liste des vehicules dans le Parc ou retiré du Parc.")
    @ApiResponse(responseCode = "201", description = "Liste des vehicules actif / non actif récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des vehicules non disponnible")
    List<Record> vehiculesRetirerDuParc(Boolean retirerDuParc) {
        try {
            List<Record> listeVehiculesRetirerDuParc = vehiculeService.retirerDuParc(retirerDuParc);
            logger.info("La liste des véhicules retiré du parc ou dans le parc récupérée avec succès");
            return listeVehiculesRetirerDuParc;
        } catch (VehiculeException e) {
            logger.error("Echec de la récupération de la liste des véhicules retirer ou dans le parc");
            throw new LocationException(e.getMessage());
        }
    }
}
