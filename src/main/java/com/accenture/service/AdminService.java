package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;


import java.util.List;

public interface AdminService {

    AdminResponseDto ajouter(AdminRequestDto adminRequestDto);

    List<AdminResponseDto> liste();

   AdminResponseDto trouver(Long id) throws ClientException;



}
