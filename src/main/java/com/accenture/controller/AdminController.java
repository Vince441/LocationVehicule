package com.accenture.controller;

import com.accenture.exception.AdminException;
import com.accenture.service.AdminService;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/liste")
    @Operation(summary = "Liste des administrateurs", description = "Permet de voir la liste des admins.")
    @ApiResponse(responseCode = "201", description = "Liste des admins récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des admins non disponnible")
    public List<AdminResponseDto> tous() {
        try {
            List<AdminResponseDto> admins = adminService.trouverToutes();
            logger.info("La liste des administrateurs récupérée avec succès");
            return admins;
        } catch (AdminException e) {
            logger.error("Erreur lors de la récupération de la liste des administrateurs");
            throw new AdminException(e.getMessage());
        }
    }


    @GetMapping("/recupInfo/{email}")
    @Operation(summary = "Informations du compte", description = "Permet de voir les informations de son compte.")
    @ApiResponse(responseCode = "201", description = "Recuperation du compte admin")
    @ApiResponse(responseCode = "400", description = "Compte admin non disponnible")
    public ResponseEntity<AdminResponseDto> unAdmin(@PathVariable("email") String email, @RequestParam String password) {
        try {
            AdminResponseDto trouve = adminService.trouver(email, password);
            logger.info("Administrateur trouvé avec succès pour l'email: {}", email);
            return ResponseEntity.ok(trouve);
        } catch (AdminException e) {
            logger.error("Erreur lors de la récupération de l'administrateur pour l'email: {}", email);
            throw new AdminException(e.getMessage());
        }
    }


    @PostMapping
    @Operation(summary = "Créer un administrateur", description = "Permet d'ajouter un compte admin.")
    @ApiResponse(responseCode = "201", description = "Ajout du compte réussis")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<Void> ajouterAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        try {
            adminService.ajouter(adminRequestDto);
            logger.info("Administrateur créer avec succès");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (AdminException e) {
            logger.error("Erreur lors de la création de l'administrateur");
            throw new AdminException(e.getMessage());
        }
    }


    @PatchMapping("/{email}")
    @Operation(summary = "Modifier d'un admin partiellement", description = "Modification partiellement de mon compte")
    @ApiResponse(responseCode = "200", description = "Modification du compte réussis")
    @ApiResponse(responseCode = "400", description = "Echec lors de la modification du compte")
    public ResponseEntity<AdminResponseDto> modifierPartiellementUnAdmin(@PathVariable("email") String email, @RequestParam String password, @RequestBody AdminRequestDto adminRequestDto) {
        try {
            AdminResponseDto response = adminService.modifierPartiellement(email, password, adminRequestDto);
            logger.info("L admin à bien été modifier partiellement");
            return ResponseEntity.ok(response);
        } catch (AdminException e) {
            logger.error("Erreur lors de la modification de l'administrateur");
            throw new AdminException(e.getMessage());
        }
    }


    @DeleteMapping("/{email}")
    @Operation(summary = "Supprimer un administrateur", description = "Permet de supprimer un compte admin.")
    @ApiResponse(responseCode = "204", description = "Supression du compte réussis")
    @ApiResponse(responseCode = "400", description = "Supression impossible")
    public ResponseEntity<Void> supprimerAdmin(@PathVariable("email") String email, @RequestParam String password) {
        try {
            adminService.supprimer(email, password);
            logger.info("Administrateur supprimé avec succès pour l'email: {}", email);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (AdminException e) {
            logger.error("Erreur lors de la suppression de l'administrateur pour l'email: {}", email);
            throw new AdminException(e.getMessage());
        }
    }
}


