package com.accenture.controller;

import com.accenture.service.AdminService;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;



    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    List<AdminResponseDto> tous (){ return adminService.trouverToutes();}

    @GetMapping("/{id}")
    ResponseEntity<AdminResponseDto> unClient(@PathVariable("id") String email){
        AdminResponseDto trouve = adminService.trouver(email);
                return ResponseEntity.ok(trouve);
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody AdminRequestDto adminRequestDto){
        adminService.ajouter(adminRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<AdminResponseDto> modifier(@PathVariable("id") String email, @RequestBody @Valid AdminRequestDto adminRequestDto) {
        AdminResponseDto reponse = adminService.modifier(email, adminRequestDto);
        return ResponseEntity.ok(reponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<AdminResponseDto> supprimer(@PathVariable("id") String email, String password){
        adminService.supprimer(email, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
