package com.accenture.controller.vehicules;


import com.accenture.repository.entity.vehicules.Vehicule;
import com.accenture.service.dto.vehicules.VoitureRequestDto;
import com.accenture.service.dto.vehicules.VoitureResponseDto;
import com.accenture.service.vehicules.VoitureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicules/voiture")
public class VoitureController {

    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

@GetMapping
    List<VoitureResponseDto> tous(){
        return voitureService.trouverToute();
}

@GetMapping("/rechercheActif")
List<VoitureResponseDto> tousActif(Boolean actif){

        return voitureService.trouverActif(actif);
}


@GetMapping("/recupVoiture/{id}")
ResponseEntity<VoitureResponseDto> uneVoiture(@PathVariable("id") int id){
    VoitureResponseDto trouve = voitureService.trouverUneVoiture(id);
        return ResponseEntity.ok(trouve);
}

@PostMapping
    ResponseEntity<Void> ajouter(@RequestBody VoitureRequestDto voitureRequestDto){
        voitureService.ajouter(voitureRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
}


}
