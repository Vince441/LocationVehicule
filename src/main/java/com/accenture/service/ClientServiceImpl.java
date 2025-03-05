package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdresseDto;
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


    /**
     * Ajoute un nouveau client à la base de données.
     * La méthode vérifie que les informations du client sont valides avant de les enregistrer.
     * Si les informations sont valides, un nouveau client est créé et ajouté à la base de données.
     *
     * @param clientRequestDto l'objet {@link ClientRequestDto} contenant les informations du client à ajouter.
     * @return un objet {@link ClientResponseDto} représentant le client ajouté.
     * @throws ClientException         si les informations du client ne sont pas valides.
     * @throws EntityNotFoundException si une entité liée (comme l'adresse) n'est pas trouvée.
     */
    @Override
    public ClientResponseDto ajouter(ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {
        verifierClient(clientRequestDto);
        Adresse adresse = adressMapper.toAdresse(clientRequestDto.adresse());
        Client client = clientMapper.toClient(clientRequestDto);

        client.setAdresse(adresse);
        Client clientEnreg = clientDao.save(client);

        return clientMapper.toClientResponseDto(clientEnreg);
    }


    /**
     * Récupère tous les clients de la base de données et les retourne sous forme d'une liste de {@link ClientResponseDto}.
     * Chaque client est mappé depuis l'entité {@link Client} vers un DTO {@link ClientResponseDto}.
     *
     * @return une liste de {@link ClientResponseDto} représentant tous les clients dans la base de données.
     */
    @Override
    public List<ClientResponseDto> trouverToutes() {
        return clientDao
                .findAll()
                .stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }


    /**
     * Recherche un client dans la base de données en fonction de son email et de son mot de passe.
     * Si un client correspondant est trouvé, un DTO représentant ce client est retourné.
     * Si aucun client n'est trouvé avec les informations données, une exception est levée.
     *
     * @param email    l'adresse email du client à rechercher.
     * @param password le mot de passe du client à rechercher.
     * @return un objet {@link ClientResponseDto} représentant le client trouvé.
     * @throws EntityNotFoundException si aucun client n'est trouvé avec l'email et le mot de passe fournis.
     */
    @Override
    public ClientResponseDto trouver(String email, String password) throws EntityNotFoundException {
        Optional<Client> optClient = clientDao.findByEmailAndPassword(email, password);
        if (optClient.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_EMAIL);
        Client client = optClient.get();
        return clientMapper.toClientResponseDto((client));
    }


    /**
     * Modifie les informations d'un client existant dans la base de données.
     * La méthode vérifie que le client existe en fonction de son email et mot de passe, puis met à jour ses informations avec celles fournies dans le DTO {@link ClientRequestDto}.
     * Si le client n'est pas trouvé, une exception est levée.
     *
     * @param email            l'email du client à modifier.
     * @param password         le mot de passe du client à modifier.
     * @param clientRequestDto un objet {@link ClientRequestDto} contenant les nouvelles informations du client.
     * @return un objet {@link ClientResponseDto} représentant le client modifié.
     * @throws ClientException         si les informations du client ne sont pas valides.
     * @throws EntityNotFoundException si aucun client n'est trouvé avec l'email et le mot de passe fournis.
     */
    @Override
    public ClientResponseDto modifier(String email, String password, ClientRequestDto clientRequestDto) throws ClientException {
        clientDao.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_EMAIL));

        verifierClient(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        client.setEmail(email);
        Client clientEnreg = clientDao.save(client);

        return clientMapper.toClientResponseDto(clientEnreg);
    }

    /**
     * Supprime un client de la base de données en fonction de son email et de son mot de passe.
     * Si le client correspondant est trouvé, il est supprimé. Sinon, une exception est levée.
     *
     * @param email    l'email du client à supprimer.
     * @param password le mot de passe du client à supprimer.
     * @throws EntityNotFoundException si aucun client n'est trouvé avec l'email et le mot de passe fournis.
     */
    @Override
    public void supprimer(String email, String password) throws ClientException {
        Client client = clientDao.findByEmailAndPassword(email, password).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        clientDao.delete(client);
    }


    /**
     * Modifie partiellement les informations d'un client existant à partir des données fournies dans le {@link ClientRequestDto}.
     *
     * Cette méthode recherche un client en fonction de son email et de son mot de passe. Si le client est trouvé, seuls les champs non-nuls
     * dans le {@link ClientRequestDto} sont appliqués à l'objet `Client` existant. Ensuite, le client mis à jour est sauvegardé dans la base de données.
     *
     * @param email L'email du client à rechercher.
     * @param password Le mot de passe du client à rechercher.
     * @param clientRequestDto L'objet contenant les données à mettre à jour pour le client.
     *
     * @return {@link ClientResponseDto} L'objet représentant le client mis à jour après modification.
     *
     * @throws ClientException Si un problème survient lors de la modification du client (par exemple, si le client n'est pas trouvé).
     * @throws EntityNotFoundException Si aucun client n'est trouvé avec l'email et le mot de passe fournis.
     */
    @Override
    public ClientResponseDto modifierPartiellement(String email, String password, ClientRequestDto clientRequestDto) throws ClientException {
        Optional<Client> optClient = clientDao.findByEmailAndPassword(email, password);
        if (optClient.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_EMAIL);

        Client clientExistant = optClient.get();
        Client clientEnreg = clientMapper.toClient(clientRequestDto);

        Adresse adresseExistant = optClient.get().getAdresse();
        Adresse adresseEnreg = adressMapper.toAdresse(clientRequestDto.adresse());

        remplacer(clientExistant, clientEnreg, adresseExistant, adresseEnreg);

        Client modifClient = clientDao.save(clientExistant);
        return clientMapper.toClientResponseDto(modifClient);

    }


    /**
     * Met à jour les informations de l'objet `clientExistant` avec les valeurs non-nulles provenant de `clientEnreg` et `adresseEnreg`.

     * Cette méthode compare chaque champ de l'objet `clientEnreg` et `adresseEnreg` avec les valeurs correspondantes de `clientExistant`.
     * Si une valeur non-nulle est trouvée dans `clientEnreg` ou `adresseEnreg`, elle est utilisée pour mettre à jour le champ correspondant de `clientExistant` ou `adresseExistant`.
     *
     * @param clientExistant L'objet `Client` existant à mettre à jour.
     * @param clientEnreg L'objet `Client` contenant les nouvelles valeurs à appliquer.
     * @param adresseExistant L'objet `Adresse` existant à mettre à jour.
     * @param adresseEnreg L'objet `Adresse` contenant les nouvelles valeurs à appliquer.
     */
    private static void remplacer(Client clientExistant, Client clientEnreg, Adresse adresseExistant, Adresse adresseEnreg) {
        if (clientEnreg.getNom() != null)
            clientExistant.setNom(clientEnreg.getNom());
        if (clientEnreg.getPrenom() != null)
            clientExistant.setPrenom(clientEnreg.getPrenom());
        if (clientEnreg.getEmail() != null)
            clientExistant.setEmail(clientEnreg.getEmail());
        if (clientEnreg.getPassword() != null)
            clientExistant.setPassword(clientEnreg.getPassword());
        if (adresseEnreg.getRue() != null)
            adresseExistant.setRue(adresseEnreg.getRue());
        if (adresseEnreg.getCodePostal() != null)
            adresseExistant.setCodePostal(adresseEnreg.getCodePostal());
        if (adresseEnreg.getVille() != null)
            adresseExistant.setVille(adresseEnreg.getVille());
        if (clientEnreg.getDateDeNaissance() != null)
            clientExistant.setDateDeNaissance(clientEnreg.getDateDeNaissance());
        if (clientEnreg.getPermis() != null)
            clientExistant.setPermis(clientEnreg.getPermis());
        if (clientEnreg.getDesactive() != null)
            clientExistant.setDesactive(clientEnreg.getDesactive());

    }


    /**
     * Vérifie si un utilisateur est âgé de 18 ans ou plus à partir de sa date de naissance.
     *
     * @param dateDeNaissance la date de naissance de l'utilisateur à vérifier.
     * @return {@code true} si l'utilisateur a 18 ans ou plus, sinon {@code false}.
     * Retourne {@code false} si la date de naissance est {@code null}.
     */
    private static boolean ageRequis(LocalDate dateDeNaissance) {
        if (dateDeNaissance == null) {
            return false;
        }
        return Period.between(dateDeNaissance, LocalDate.now()).getYears() >= 18;
    }


    /**
     * Vérifie la validité des informations d'un client fournies dans le DTO {@link ClientRequestDto}.
     * La méthode valide chaque champ du DTO pour s'assurer que toutes les informations nécessaires sont présentes et correctes.
     * Si l'une des informations est invalide, une exception {@link ClientException} est levée.
     *
     * @param clientRequestDto un objet {@link ClientRequestDto} contenant les informations à vérifier.
     * @throws ClientException si l'une des informations est invalide ou manquante, une exception est levée avec un message explicite.
     */
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
