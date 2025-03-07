package com.accenture.controller;

import com.accenture.exception.ClientException;
import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/liste")
    @Operation(summary = "Liste des clients", description = "Permet de voir la liste de toute les clients.")
    @ApiResponse(responseCode = "201", description = "Liste des clients récuperé avec succès")
    @ApiResponse(responseCode = "400", description = "Liste des clients non disponnible")
    public List<ClientResponseDto> trouverTousLesClients() {
        try {
            List<ClientResponseDto> client = clientService.trouverToutes();
            logger.info("La liste des clients récupérée avec succès");
            return client;
        } catch (ClientException e) {
            logger.error("Erreur lors de la récupération de la liste des clients");
            throw new ClientException(e.getMessage());
        }
    }

    @GetMapping("/recupInfo/{email}")
    @Operation(summary = "Informations du compte", description = "Permet de voir les informations de son compte.")
    @ApiResponse(responseCode = "201", description = "Compte affiché avec succès")
    @ApiResponse(responseCode = "400", description = "Compte non disponnible")
    public ResponseEntity<ClientResponseDto> trouverUnClient(@PathVariable("email") String email, @RequestParam String password) {
        try {
            ClientResponseDto trouve = clientService.trouver(email, password);
            logger.info("Client trouvé avec succès pour l'email: {}", email);
            return ResponseEntity.ok(trouve);
        } catch (ClientException e) {
            logger.error("Erreur lors de la récupération de l'administrateur pour l'email: {}", email);
            throw new ClientException(e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Créer un client", description = "Permet de créer un client")
    @ApiResponse(responseCode = "201", description = "Compte créer avec succès")
    @ApiResponse(responseCode = "400", description = "Echec lors de la création du compte")
    public ResponseEntity<Void> ajouterClient(@RequestBody ClientRequestDto clientRequestDto) {
        try {
            clientService.ajouter(clientRequestDto);
            logger.info("Client créé avec succès");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ClientException e) {
            logger.error("Erreur lors de la création du client", e);
            throw new ClientException(e.getMessage());
        }
    }

    @PatchMapping("/{email}")
    @Operation(summary = "Modifier d'un client partiellement", description = "Modification partiellement de mon compte")
    @ApiResponse(responseCode = "200", description = "Modification du compte réussis")
    @ApiResponse(responseCode = "400", description = "Echec lors de la modification du compte")
    public ResponseEntity<ClientResponseDto> modifierPartiellementUnClient(@PathVariable("email") String email, @RequestParam String password, @RequestBody ClientRequestDto clientRequestDto) {
        try {
            ClientResponseDto response = clientService.modifierPartiellement(email, password, clientRequestDto);
            logger.info("Le client à bien été modifier partiellement");
            return ResponseEntity.ok(response);
        } catch (ClientException e) {
            logger.error("Erreur lors de la modification du client");
            throw new ClientException(e.getMessage());
        }
    }


    @DeleteMapping("/{email}")
    @Operation(summary = "Supprimer un client", description = "Permet de supprimer un compte client.")
    @ApiResponse(responseCode = "204", description = "Supression du compte réussis")
    @ApiResponse(responseCode = "400", description = "Echec lors de la supression du compte")
    public ResponseEntity<ClientResponseDto> supprimerClient(@PathVariable("email") String email, @RequestParam String password) {
        try {
            clientService.supprimer(email, password);
            logger.info("Client supprimé avec succès pour l'email: {}", email);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ClientException e) {
            logger.error("Erreur lors de la suppression du client pour l'email: {}", email);
            throw new ClientException(e.getMessage());
        }
    }
}


