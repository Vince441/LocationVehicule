package com.accenture.controller;

import com.accenture.service.AdminService;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
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
    List<AdminResponseDto> tous (){ return adminService.liste();}

    @GetMapping("/{id}")
    ResponseEntity<AdminResponseDto> unClient(@PathVariable("id") Long id){
        AdminResponseDto trouve = adminService.trouver(id);
                return ResponseEntity.ok(trouve);
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody AdminRequestDto adminRequestDto){
        adminService.ajouter(adminRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
