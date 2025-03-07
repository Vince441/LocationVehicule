package com.accenture.service;


import com.accenture.exception.ClientException;
import com.accenture.model.Permis;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdresseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.AdresseMapper;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImplTest.class);


    @Mock
    ClientDao daoMock;


    @Mock
    ClientMapper mapperMock;

    @Mock
    AdresseMapper adresseMapper;


    @InjectMocks
    ClientServiceImpl service;

    @Test
    void testAjouter() {
        assertThrows(ClientException.class, () -> service.ajouter(null));
        logger.info("L'ajout est bien null");
    }


    @DisplayName("""
            Test de la méthode trouver (String mail) qui doit renvoyer une exception lorsque l'utilisateur' n'existe pas en bas;
            """)
    @Test
    void testTrouverExistePas() {
        Mockito.when(daoMock.findByEmailAndPassword("bbbb@gmail.fr", "z")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver("bbbb@gmail.fr", "z"));
        assertEquals("Je n'ai pas trouvé l'email", ex.getMessage());
        logger.info(ex.getMessage());
    }


    @DisplayName("""
            Test de la méthode trouver (String email) qui doit renvoyer un ClientResponseDto lorsque le client existe en base
            """)
    @Test
    void testTrouverExiste() {

        Client c = creeClient();
        Optional<Client> optClient = Optional.of(c);
        Mockito.when(daoMock.findByEmailAndPassword("b@gmail.fr", "z")).thenReturn(optClient);

        ClientResponseDto dto = creeClientResponseDto();
        Mockito.when(mapperMock.toClientResponseDto(c)).thenReturn(dto);
        assertSame(dto, service.trouver("b@gmail.fr", "z"));
        logger.info("Utilisateur trouvé");
    }


    @DisplayName("""
            Test de la methode trouverToutes qui doit renvoyer une liste de ClientResponseDto correspondant aux utilisateur existant en base de donnée
            """)
    @Test
    void testTrouverToutes() {
        Client client1 = creeClient();
        Client client2 = creeClient2();

        List<Client> clients = List.of(creeClient(), creeClient2());

        ClientResponseDto clientResponseDtoClient1 = creeClientResponseDto();
        ClientResponseDto clientResponseDtoClient2 = creeClientResponseDto2();

        List<ClientResponseDto> dtos = List.of(clientResponseDtoClient1, clientResponseDtoClient2);

        Mockito.when(daoMock.findAll()).thenReturn(clients);
        Mockito.when(mapperMock.toClientResponseDto(client1)).thenReturn(creeClientResponseDto());
        Mockito.when(mapperMock.toClientResponseDto(client2)).thenReturn(creeClientResponseDto2());

        assertEquals(dtos, service.trouverToutes());
        logger.info("Liste des clients trouvé");
    }


    @DisplayName("""
            Test de la méthode ajouter
            """)
    @Test
    void testAjouterOk() {
        ClientRequestDto requestDto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre",
                new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24), List.of(Permis.A), true);
        Client clientAvantEnreg = creeClient();


        Client clientApresEnreg = creeClient();
        ClientResponseDto responseDto = creeClientResponseDto();

        Mockito.when(adresseMapper.toAdresse(requestDto.adresse())).thenReturn(new Adresse());
        Mockito.when(mapperMock.toClient(requestDto)).thenReturn(clientAvantEnreg);
        Mockito.when(daoMock.save(clientAvantEnreg)).thenReturn(clientApresEnreg);
        Mockito.when(mapperMock.toClientResponseDto(clientApresEnreg)).thenReturn(responseDto);

        assertSame(responseDto, service.ajouter(requestDto));
        logger.info("Ajout réussis");
    }

    @DisplayName("""
            Test de la methode supprimer qui doit supprimer un client
            """)
    @Test
    void testSupprimerExiste() {

        Client client = creeClient();
        String email = client.getEmail();
        String password = client.getPassword();
        Mockito.when(daoMock.findByEmailAndPassword(email, password)).thenReturn(Optional.of(client));
        service.supprimer(email, password);
        Mockito.verify(daoMock, Mockito.times(1)).delete(client);
        logger.info("Le client est supprimé");
    }


    @DisplayName("""
            Test de la methode supprimer qui doit supprimer un client
            """)
    @Test
    void testSupprimerExistePas() {

        Mockito.when(daoMock.findByEmailAndPassword("tp@gmail.fr", "s")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.supprimer("tp@gmail.fr", "s"));
        assertEquals("Utilisateur non trouvé", ex.getMessage());
        logger.info("Le client ne peux pas être supprimé car il existe pas");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans nom (null) exception levée
            """)
    @Test
    void testAjouterNomNull() {
        ClientRequestDto dto = new ClientRequestDto(null, "V", "v@gmail.fr", "ocre", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le nom est null");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans nom (blank) exception levée
            """)
    @Test
    void testAjouterSansNomBlank() {
        ClientRequestDto dto = new ClientRequestDto("\n", "V", "v@gmail.fr", "ocre", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le nom est vide");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans prenom (null) exception levée
            """)
    @Test
    void testAjouterPrenomNull() {
        ClientRequestDto dto = new ClientRequestDto("L", null, "v@gmail.fr", "ocre", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le prenom est null");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans prenom (blank) exception levée
            """)
    @Test
    void testAjouterSansPrenomBlank() {
        ClientRequestDto dto = new ClientRequestDto("L", "\n", "v@gmail.fr", "ocre", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le prenom est vide");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans email (null) exception levée
            """)
    @Test
    void testAjouterEmailNull() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", null, "ocre", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("L'email est null");
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto sans email (blank) exception levée
            """)
    @Test
    void testAjouterSansEmailBlank() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "\n", "ocre", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("L'email est vide");
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto sans password (null) exception levée
            """)
    @Test
    void testAjouterPasswordNull() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", null, new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le password est null");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans password (blank) exception levée
            """)
    @Test
    void testAjouterSansPasswordBlank() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "\n", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le password est vide");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans Adresse rue (null) exception levée
            """)
    @Test
    void testAjouterAdressRueNull() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto(null, "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("L'Adresse est null");
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto sans Adresse codePostal (null) exception levée
            """)
    @Test
    void testAjouterAdressCodePostalNull() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("sdfs", null, "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le code postal est null");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans Adresse ville (null) exception levée
            """)
    @Test
    void testAjouterAdressVilleNull() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("sdfs", "44000", null),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("La ville est null, Nantes n'est pas en Bretagne");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans Adresse rue (blank) exception levée
            """)
    @Test
    void testAjouterSansAdressRueBlank() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("\n", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("La rue est vide");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans Adresse codePostal (blank) exception levée
            """)
    @Test
    void testAjouterSansAdressCodePostalBlank() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("a", "\n", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le code postal est vide");
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto sans Adresse ville (blank) exception levée
            """)
    @Test
    void testAjouterSansAdressVilleBlank() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("a", "sdfsdf", "\n"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("La ville est vide");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans Date de naissance null) exception levée
            """)
    @Test
    void testAjouterSansDateDeNaissanceNull() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("a", "44000", "Nantes"),
                null,
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("La date de naissance est null");
    }

    @Test
    void testAjouterMineur() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("a", "44000", "Nantes"),
                LocalDate.of(2025, 12, 24),
                List.of(Permis.A), true);
        ClientException exception = assertThrows(ClientException.class, () -> service.ajouter(dto));
        assertEquals("L'utilisateur doit avoir 18 ans", exception.getMessage());
        logger.info("L'utilisateur est mineur");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans Permis null) exception levée
            """)
    @Test
    void testAjouterSansPermisNull() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("a", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                null, true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le permis est null, repasse le !");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans Permis isEmpty) exception levée
            """)
    @Test
    void testAjouterSansPermisEmpty() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("a", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le permis est vide");
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans Desactive null) exception levée
            """)
    @Test
    void testAjouterSansDesactive() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("a", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), null);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
        logger.info("Le desactive est null");
    }


    @DisplayName("""
            Test modifier partiellement un client / ok
            """)
    @Test
    void testModifierPartiellementOk() throws ClientException, EntityNotFoundException {
        String email = "test@test.com";
        String password = "azertyuiop";

        Client clientExistant = creeClient();
        ClientResponseDto clientEnreg = creeClientResponseDto();

        ClientRequestDto clientRequestDto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);


        Mockito.when(daoMock.findByEmailAndPassword(email, password)).thenReturn(Optional.of(clientExistant));
        Mockito.when(mapperMock.toClient(clientRequestDto)).thenReturn(clientExistant);
        Mockito.when(daoMock.save(clientExistant)).thenReturn(clientExistant);
        Mockito.when(mapperMock.toClientResponseDto(clientExistant)).thenReturn(clientEnreg);

        ClientResponseDto result = service.modifierPartiellement(email, password, clientRequestDto);
        assertNotNull(result);
        assertEquals(clientEnreg, result);
        Mockito.verify(daoMock).findByEmailAndPassword(email, password);
        Mockito.verify(mapperMock).toClient(clientRequestDto);
        Mockito.verify(daoMock).save(clientExistant);
        Mockito.verify(mapperMock).toClientResponseDto(clientExistant);
        logger.info("Lutilisateur à bien été modifié");
    }

    @DisplayName("Test modifier partiellement un client / client non trouvé")
    @Test
    void testModifierPartiellementClientNonTrouve() {

        String mail = "test@test.com";
        String password = "azertyuiop";

        ClientRequestDto clientRequestDto = new ClientRequestDto("L", "V", "v@gmail.fr", "\n", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);

        Mockito.when(daoMock.findByEmailAndPassword(mail, password)).thenReturn(Optional.empty());
        assertThrows(ClientException.class, () -> service.modifierPartiellement(mail, password, clientRequestDto));
        Mockito.verify(daoMock).findByEmailAndPassword(mail, password);
        Mockito.verify(mapperMock, never()).toClient(any());
        Mockito.verify(daoMock, never()).save(any());
        Mockito.verify(mapperMock, never()).toClientResponseDto(any());
        logger.info("Lutilisateur n'a pas bien été modifié");
    }


    private static Client creeClient() {
        Client c = new Client();
        c.setNom("L");
        c.setPrenom("V");
        c.setEmail("v@gmail.fr");
        c.setPermis(List.of(Permis.A));
        c.setPassword("ocre");
        c.setAdresse(new Adresse(1L, "29 rue de la bibine", "44000", "Nantes"));
        c.setDateDeNaissance(LocalDate.of(1990, 12, 24));
        c.setDateInscription(LocalDate.of(2025, 2, 21));
        c.setDesactive(true);
        return c;
    }

    private static Client creeClient2() {
        Client c = new Client();
        c.setNom("Ldp");
        c.setPrenom("A");
        c.setEmail("a@gmail.fr");
        c.setPermis(List.of(Permis.A));
        c.setPassword("test");
        c.setAdresse(new Adresse(2L, "29bis rue de la bibine", "44000", "Nantes"));
        c.setDateDeNaissance(LocalDate.of(1992, 12, 24));
        c.setDateInscription(LocalDate.of(2025, 3, 24));
        c.setDesactive(true);
        return c;
    }


    private static ClientResponseDto creeClientResponseDto() {

        return new ClientResponseDto("L", "V", "v@gmail.fr",
                new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24), List.of(Permis.A), true);
    }

    private static ClientResponseDto creeClientResponseDto2() {

        return new ClientResponseDto("Ldp", "A", "a@gmail.fr",
                new AdresseDto("29bis rue de la bibine", "44100", "Saint-Herblain"),
                LocalDate.of(1992, 12, 24), List.of(Permis.A), true);
    }
}