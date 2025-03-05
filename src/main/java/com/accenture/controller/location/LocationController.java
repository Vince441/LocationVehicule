package com.accenture.controller.location;


import com.accenture.exception.locations.LocationException;
import com.accenture.service.dto.locations.LocationRequestDto;
import com.accenture.service.dto.locations.LocationResponseDto;
import com.accenture.service.location.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/vehicules/date")
public class LocationController {

    private final LocationService locationService;
    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/rechercheVehiculeParDate")
    @Operation(summary = "Liste des locations par date", description = "Permet de recuperer les locations par date")
    @ApiResponse(responseCode = "201", description = "Liste des locations récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des locations non disponnible")
    public List<LocationResponseDto> rechercheLocationParDate(@RequestParam LocalDate dateDeDebut, @RequestParam LocalDate dateDeFin) {
        try {
            List<LocationResponseDto> location = locationService.trouverParDate(dateDeDebut, dateDeFin);

            logger.info("Vehicule avec réservé à cette date trouvé : {} et {}", dateDeDebut, dateDeFin);
            return location;
        } catch (LocationException e) {
            logger.error("Erreur lors de la récupération de la location");
            throw new LocationException(e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Ajoute des locations", description = "Permet d'ajouter des locations.")
    @ApiResponse(responseCode = "201", description = "Liste des locations récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des locations non disponnible")
    public ResponseEntity<Void> ajouterLocation(@RequestBody LocationRequestDto locationRequestDto) {
        try {
            locationService.ajouter(locationRequestDto);
            logger.info("Location ajoutée avec succès");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (LocationException e) {
            logger.error("Erreur lors de la création de la location", e);
            throw new LocationException(e.getMessage());
        }
    }
}
