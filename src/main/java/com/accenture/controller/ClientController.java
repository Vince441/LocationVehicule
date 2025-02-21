package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
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
        return clientService.liste();
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientResponseDto> unClient(@PathVariable("id") Long id) {
        ClientResponseDto trouve = clientService.trouver(id);
        return ResponseEntity.ok(trouve);
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody ClientRequestDto clientRequestDto) {
        clientService.ajouter(clientRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}


