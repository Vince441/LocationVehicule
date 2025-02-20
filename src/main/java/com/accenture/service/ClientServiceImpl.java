package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Adresse;

import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.AdresseMapper;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    public static final String JE_N_AI_PAS_TROUVER_L_ID = "Je n'ai pas trouvé l'id";
    private final ClientDao clientDao;
    private final ClientMapper clientMapper;
    private final AdresseMapper adressMapper;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper, AdresseMapper adressMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
        this.adressMapper = adressMapper;
    }


    @Override
    public ClientResponseDto ajouter(ClientRequestDto clientRequestDto) {
        Adresse adresse = adressMapper.toAdresse(clientRequestDto.adresse());
        Client client = clientMapper.toClient(clientRequestDto);

        client.setAdresse(adresse);
        Client clientEnreg = clientDao.save(client);

        return clientMapper.toClientResponseDto(clientEnreg);
    }

    @Override
    public List<ClientResponseDto> liste() {
        return clientDao
                .findAll()
                .stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }


    @Override
    public ClientResponseDto trouver(Long id) throws ClientException {
        Optional<Client> optClient = clientDao.findById(id);
        if(optClient.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);
        Client client = optClient.get();
        return clientMapper.toClientResponseDto((client));
    }
}
