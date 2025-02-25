package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {

ClientResponseDto ajouter(ClientRequestDto clientRequestDto);

List<ClientResponseDto> trouverToutes();

ClientResponseDto trouver(String email, String password) throws ClientException;

ClientResponseDto modifier(String email, ClientRequestDto clientRequestDto) throws ClientException;

void supprimer(String email, String password) throws ClientException;


}
