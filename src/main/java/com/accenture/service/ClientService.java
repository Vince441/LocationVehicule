package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {

    ClientResponseDto ajouter(ClientRequestDto clientRequestDto) throws ClientException;

    List<ClientResponseDto> trouverToutes() throws ClientException;

    ClientResponseDto trouver(String email, String password) throws ClientException;

    ClientResponseDto modifier(String email, String password, ClientRequestDto clientRequestDto) throws ClientException;

    void supprimer(String email, String password) throws ClientException;

}
