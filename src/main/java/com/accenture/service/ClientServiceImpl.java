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

    public static final String JE_N_AI_PAS_TROUVER_L_EMAIL = "Je n'ai pas trouvé l'email";
    private final ClientDao clientDao;
    private final ClientMapper clientMapper;
    private final AdresseMapper adressMapper;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper, AdresseMapper adressMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
        this.adressMapper = adressMapper;
    }


    @Override
    public ClientResponseDto ajouter(ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {
        verifierClient(clientRequestDto);
        Adresse adresse = adressMapper.toAdresse(clientRequestDto.adresse());
        Client client = clientMapper.toClient(clientRequestDto);

        client.setAdresse(adresse);
        Client clientEnreg = clientDao.save(client);

        return clientMapper.toClientResponseDto(clientEnreg);
    }

    @Override
    public List<ClientResponseDto> trouverToutes() {
        return clientDao
                .findAll()
                .stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }


    @Override
    public ClientResponseDto trouver(String email) throws EntityNotFoundException {
        Optional<Client> optClient = clientDao.findById(email);
        if (optClient.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_EMAIL);
        Client client = optClient.get();
        return clientMapper.toClientResponseDto((client));
    }

    @Override
    public ClientResponseDto modifier(String email, ClientRequestDto clientRequestDto) throws ClientException {
        if (!clientDao.existsById(email))
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_EMAIL);
        verifierClient(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        client.setEmail(email);
        Client clientEnreg = clientDao.save(client);

        return clientMapper.toClientResponseDto(clientEnreg);
    }

    @Override
    public void supprimer(String email, String password) throws ClientException {
Client client = clientDao.findByEmailAndPassword(email, password).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
clientDao.delete(client);
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
            throw new ClientException("le nom est obligatoire");
        if (clientRequestDto.prenom() == null || clientRequestDto.prenom().isBlank())
            throw new ClientException("le prenom est obligatoire");
        if (clientRequestDto.email() == null || clientRequestDto.email().isBlank())
            throw new ClientException("L'email est obligatoire");
        if (clientRequestDto.password() == null || clientRequestDto.password().isBlank())
            throw new ClientException("le password est obligatoire");
        if (clientRequestDto.adresse().rue() == null ||
                clientRequestDto.adresse().codePostal() == null ||
                clientRequestDto.adresse().ville() == null ||
                clientRequestDto.adresse().rue().isBlank() ||
                clientRequestDto.adresse().codePostal().isBlank() ||
                clientRequestDto.adresse().ville().isBlank())
            throw new ClientException("L'adresse est obligatoire");
        if (clientRequestDto.dateDeNaissance() == null)
            throw new ClientException("La date est obligatoire");
        if (!ageRequis(clientRequestDto.dateDeNaissance())) {
            throw new ClientException("L'utilisateur doit avoir 18 ans");
        }
        if (clientRequestDto.permis() == null || clientRequestDto.permis().isEmpty()) {
            throw new ClientException("Le permis est obligatoire");
        }
        if (clientRequestDto.desactive() == null) {
            throw new ClientException("Le desactiver est obligatoire");
        }
    }
}
