package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;



import java.util.List;

public interface AdminService {

    AdminResponseDto ajouter(AdminRequestDto adminRequestDto);

    List<AdminResponseDto> trouverToutes();

   AdminResponseDto trouver(String email, String password) throws AdminException;

    AdminResponseDto modifier(String email, AdminRequestDto adminRequestDto) throws AdminException;

    void supprimer(String email, String password) throws AdminException;



}
