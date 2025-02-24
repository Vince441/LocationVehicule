package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    List<ClientResponseDto> tous() {
        return clientService.trouverToutes();
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientResponseDto> unClient(@PathVariable("id")String email) {
        ClientResponseDto trouve = clientService.trouver(email);
        return ResponseEntity.ok(trouve);
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody ClientRequestDto clientRequestDto) {
        clientService.ajouter(clientRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<ClientResponseDto> modifier(@PathVariable("id") String email, @RequestBody @Valid ClientRequestDto clientRequestDto) {
        ClientResponseDto reponse = clientService.modifier(email, clientRequestDto);
        return ResponseEntity.ok(reponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ClientResponseDto> supprimer(@PathVariable("id") String email, String password){
        clientService.supprimer(email, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}


