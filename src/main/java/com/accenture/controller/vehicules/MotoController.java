package com.accenture.controller.vehicules;


import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.service.dto.vehicules.MotoRequestDto;
import com.accenture.service.dto.vehicules.MotoResponseDto;
import com.accenture.service.vehicules.MotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/vehicules/moto")
public class MotoController {

    private final MotoService motoService;
    private static final Logger logger = LoggerFactory.getLogger(MotoController.class);


    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping("/liste")
    @Operation(summary = "Liste des motos", description = "Permet de voir la liste de toute les motos.")
    @ApiResponse(responseCode = "201", description = "Liste des motos récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des motos non disponnible")
    public List<MotoResponseDto> trouverToutesLesMotos() {
        try {
            List<MotoResponseDto> listDesMotos = motoService.trouverToute();
            logger.info("Liste des motos récupérée avec succès");
            return listDesMotos;
        } catch (VehiculeException e) {
            logger.error("Erreur lors de la récupération de la liste des motos");
            throw new VehiculeException(e.getMessage());
        }
    }


    @PostMapping
    @Operation(summary = "Ajouter une moto", description = "Ajoute une moto dans le parc.")
    @ApiResponse(responseCode = "201", description = "Moto ajoutée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<Void> ajouterUneMoto(@RequestBody MotoRequestDto motoRequestDto) {
        try {
            motoService.ajouter(motoRequestDto);
            logger.info("Moto créée avec succès");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (VehiculeException e) {
            logger.error("Erreur lors de la création de la moto", e);
            throw new VehiculeException(e.getMessage());
        }
    }

    @GetMapping("/rechercheActif")
    @Operation(summary = "Liste des motos actif et non actif", description = "Permet de voir la liste des motos actif et non actif.")
    @ApiResponse(responseCode = "201", description = "Liste des motos actif / non actif récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des motos non disponnible")
    public List<MotoResponseDto> toutesLesMotosActifetNonActif(Boolean actif) {
        try {
            List<MotoResponseDto> listeDesMotosActifNonActif = motoService.trouverActif(actif);
            logger.info("Récupération de la liste efféctuée avec succès");
            return listeDesMotosActifNonActif;
        } catch (VehiculeException e) {
            logger.error("Echec de la récupération de la liste des motos actif et non actif");
            throw new VehiculeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperation de la moto par son ID", description = "Recupère la moto par son ID")
    @ApiResponse(responseCode = "201", description = "Récupération de la moto avec succès")
    @ApiResponse(responseCode = "400", description = "Echec lors de la récuperation de la moto")
    public ResponseEntity<MotoResponseDto> rechercherUneMoto(@PathVariable("id") int id) {
        try {
            MotoResponseDto trouve = motoService.trouverUneMoto(id);
            logger.info("Moto trouvée avec succès");
            return ResponseEntity.ok(trouve);
        } catch (VehiculeException e) {
            logger.error("Echec lors de la récupération de la moto");
            throw new VehiculeException(e.getMessage());
        }
    }


    @PatchMapping("/{id}")
    @Operation(summary = "Modifier une moto partiellement", description = "Modification d'une moto")
    @ApiResponse(responseCode = "200", description = "Modification d'une moto réussis")
    @ApiResponse(responseCode = "400", description = "Echec lors de la modification de la moto")
    public ResponseEntity<MotoResponseDto> modifierPartiellementUneMoto(@PathVariable("id") int id, @RequestBody @Valid MotoRequestDto motoRequestDto) {
        try {
            MotoResponseDto response = motoService.modifierPartiellement(id, motoRequestDto);
            logger.info("La moto à bien été modifier partiellement");
            return ResponseEntity.ok(response);
        } catch (VehiculeException e) {
            throw new VehiculeException(e.getMessage());
        }
    }




    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une moto", description = "Suppression d'une moto")
    @ApiResponse(responseCode = "201", description = "Moto supprimé avec succès")
    @ApiResponse(responseCode = "400", description = "Echec de la suppression")
    public ResponseEntity<MotoResponseDto> supprimerUneMoto(@PathVariable("id") int id) {
        try {
            motoService.supprimer(id);
            logger.info("Moto supprimée avec succès");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (VehiculeException e) {
            logger.error("Echec lors de la supression de la moto", e);
            throw new VehiculeException(e.getMessage());
        }
    }


}
