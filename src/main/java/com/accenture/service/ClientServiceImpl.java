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

import java.time.LocalDate;
import java.time.Period;
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
        verifierClient(clientRequestDto);
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
        if (optClient.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);
        Client client = optClient.get();
        return clientMapper.toClientResponseDto((client));
    }


    private static boolean ageRequis(LocalDate dateDeNaissance) {
        if (dateDeNaissance == null) {
            return false;
        }
        return Period.between(dateDeNaissance, LocalDate.now()).getYears() >= 18;
    }


    private static void verifierClient(ClientRequestDto clientRequestDto) throws ClientException {
        if (clientRequestDto == null)
            throw new ClientException("Le client est null");
        if (clientRequestDto.nom() == null || clientRequestDto.nom().isBlank())
            throw new ClientException("le libelle est avent");
        if (clientRequestDto.prenom() == null || clientRequestDto.prenom().isBlank())
            throw new ClientException("le niveau est absent");
        if (clientRequestDto.email() == null || clientRequestDto.email().isBlank())
            throw new ClientException("La date limite est absente");
        if (clientRequestDto.password() == null || clientRequestDto.password().isBlank())
            throw new ClientException("le 'termine' est absent");
        if (clientRequestDto.adresse() == null || clientRequestDto.adresse().rue().isBlank() ||
        clientRequestDto.adresse().codePostal().isBlank() || clientRequestDto.adresse().ville().isBlank())
            throw new ClientException("L'adresse est nul");
        if (clientRequestDto.dateDeNaissance() == null)
            throw new ClientException("La date ne peut être null");
        if (!ageRequis(clientRequestDto.dateDeNaissance())) {
            throw new ClientException("L'utilisateur doit avoir 18 ans");
        }


    }


}
