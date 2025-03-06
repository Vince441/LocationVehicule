package com.accenture.controller.vehicules;


import com.accenture.controller.ClientController;
import com.accenture.exception.ClientException;
import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dto.vehicules.VoitureRequestDto;
import com.accenture.service.dto.vehicules.VoitureResponseDto;
import com.accenture.service.vehicules.VoitureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/vehicules/voiture")
public class VoitureController {


    private final VoitureService voitureService;
    private static final Logger logger = LoggerFactory.getLogger(VoitureController.class);
    private static final String DONNEE_INVALIDE = "Données invalides";

    public VoitureController(VoitureService voitureService) {

        this.voitureService = voitureService;
    }


    @GetMapping("/liste")
    @Operation(summary = "Liste des voitures", description = "Permet de voir la liste de toute les voitures.")
    @ApiResponse(responseCode = "201", description = "Liste des voitures récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des voitures non disponnible")
    public List<VoitureResponseDto> trouverToutesLesVoitures() {
        try {
            List<VoitureResponseDto> listeDeToutesLesVoitures = voitureService.trouverToute();
            logger.info("La liste des voitures à été récuperée avec succès");
            return listeDeToutesLesVoitures;
        } catch (VehiculeException e) {
            logger.error("Echec lors de la récupération de la liste des voitures");
            throw new VehiculeException(e.getMessage());
        }
    }

    @GetMapping("/rechercheActif")
    @Operation(summary = "Liste des voitures actif et non actif", description = "Permet de voir la liste des voitures actif et non actif.")
    @ApiResponse(responseCode = "201", description = "Liste des voitures actif / non actif récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des voitures non disponnible")
    public List<VoitureResponseDto> tousActif(Boolean actif) {
        try {
            List<VoitureResponseDto> listeDesVoituresActifNonActif = voitureService.trouverActif(actif);
            logger.info("La liste des véhicules actif et non actif à été récupérée avec succès");
            return listeDesVoituresActifNonActif;
        } catch (VehiculeException e) {
            logger.error("Echec lors de la récupération de la liste des véhicules actif et non actif");
            throw new VehiculeException(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    @Operation(summary = "Recuperation de la voiture par son ID", description = "Recupère la voiture par son ID")
    @ApiResponse(responseCode = "201", description = "Récupération de la voiture avec succès")
    @ApiResponse(responseCode = "400", description = "Echec lors de la récuperation de la voiture")
    public ResponseEntity<VoitureResponseDto> recupererUneVoiture(@PathVariable("id") int id) {
        try {
            VoitureResponseDto trouve = voitureService.trouverUneVoiture(id);
            logger.info("Voiture trouvée avec succès");
            return ResponseEntity.ok(trouve);
        } catch (VehiculeException e) {
            logger.error("Echec lors de la récupération de la voiture");
            throw new VehiculeException(e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Ajouter une voiture", description = "Ajoute une voiture dans le parc.")
    @ApiResponse(responseCode = "201", description = "Voiture ajoutée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "500", description = "Il manque une information")

    public ResponseEntity<Void> ajouterUneVoiture(@RequestBody VoitureRequestDto voitureRequestDto) {
        try {
            voitureService.ajouter(voitureRequestDto);
            logger.info("La création de la voiture à été réalisée avec succès");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (VehiculeException e) {
            logger.error("Echec lors de la création de la voiture");
            throw new VehiculeException(e.getMessage());
        }
    }



    @PatchMapping("/{id}")
    @Operation(summary = "Modifier une voiture partiellement", description = "Modification d'un voiture")
    @ApiResponse(responseCode = "200", description = "Modification d'une voiture réussis")
    @ApiResponse(responseCode = "400", description = "Echec lors de la modification de la voiture")
    public ResponseEntity<VoitureResponseDto> modifierPartiellementUneVoiture(@PathVariable("id") int id, @RequestBody @Valid VoitureRequestDto voitureRequestDto) {
        try {
            VoitureResponseDto response = voitureService.modifierPartiellement(id, voitureRequestDto);
            logger.info("Le client à bien été modifier partiellement");
            return ResponseEntity.ok(response);
        } catch (VehiculeException e) {
            throw new VehiculeException(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une voiture", description = "Suppression d'une voiture")
    @ApiResponse(responseCode = "201", description = "Voiture supprimé avec succès")
    @ApiResponse(responseCode = "400", description = "Echec de la suppression")
    ResponseEntity<VoitureResponseDto> supprimerUneVoiture(@PathVariable("id") int id) {
        try {
            voitureService.supprimer(id);
            logger.info("La voiture à bien été supprimée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (VehiculeException e) {
            logger.info("Echec lors de la suppression de la voiture");
            throw new VehiculeException(e.getMessage());
        }
    }


}
