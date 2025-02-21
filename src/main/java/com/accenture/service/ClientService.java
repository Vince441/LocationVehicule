package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {

ClientResponseDto ajouter(ClientRequestDto clientRequestDto);

List<ClientResponseDto> liste();

ClientResponseDto trouver(Long id) throws ClientException;


}
