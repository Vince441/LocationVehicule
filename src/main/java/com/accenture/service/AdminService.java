package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;


import java.util.List;

public interface AdminService {

    AdminResponseDto ajouter(AdminRequestDto adminRequestDto);

    List<AdminResponseDto> trouverToutes();

   AdminResponseDto trouver(String email) throws ClientException;

    AdminResponseDto modifier(String email, AdminRequestDto adminRequestDto) throws ClientException;

    void supprimer(String email, String password) throws ClientException;



}
