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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {


    @Mock
    ClientDao daoMock;


    @Mock
    ClientMapper mapperMock;

    @Mock
    AdresseMapper adresseMapper;


    @InjectMocks
    ClientServiceImpl service;


    @DisplayName("""
            Test de la méthode trouver (String mail) qui doit renvoyer une exception lorsque l'utilisateur' n'existe pas en bas;
            """)
    @Test
    void testTrouverExistePas() {
        Mockito.when(daoMock.findById("bbbb@gmail.fr")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver("bbbb@gmail.fr"));
        assertEquals("Je n'ai pas trouvé l'email", ex.getMessage());

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
        clientAvantEnreg.setEmail("v@gmail.fr");

        Client clientApresEnreg = creeClient();
        ClientResponseDto responseDto = creeClientResponseDto();

        Mockito.when(adresseMapper.toAdresse(requestDto.adresse())).thenReturn(new Adresse());
        Mockito.when(mapperMock.toClient(requestDto)).thenReturn(clientAvantEnreg);
        Mockito.when(daoMock.save(clientAvantEnreg)).thenReturn(clientApresEnreg);
        Mockito.when(mapperMock.toClientResponseDto(clientApresEnreg)).thenReturn(responseDto);

        assertSame(responseDto, service.ajouter(requestDto));
        Mockito.verify(daoMock, Mockito.times(1)).save(clientAvantEnreg);

    }

    @DisplayName("""
            Test de la méthode trouver (String email) qui doit renvoyer un ClientResponseDto lorsque le client existe en base
            """)
    @Test
    void testTrouverExiste() {

        Client c = creeClient();
        Optional<Client> optClient = Optional.of(c);
        Mockito.when(daoMock.findById("b@gmail.fr")).thenReturn(optClient);

        ClientResponseDto dto = creeClientResponseDto();
        Mockito.when(mapperMock.toClientResponseDto(c)).thenReturn(dto);
        assertSame(dto, service.trouver("b@gmail.fr"));

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
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans nom (null) exception levée
            """)
    @Test
    void testAjouterSansNom() {
        ClientRequestDto dto = new ClientRequestDto(null, "V", "v@gmail.fr", "ocre", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
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
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans prenom (null) exception levée
            """)
    @Test
    void testAjouterSansPrenom() {
        ClientRequestDto dto = new ClientRequestDto("L", null, "v@gmail.fr", "ocre", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
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
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans email (null) exception levée
            """)
    @Test
    void testAjouterSansEmail() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", null, "ocre", new AdresseDto("29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
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
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans Adresse rue (null) exception levée
            """)
    @Test
    void testAjouterSansAdressRue() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto(null, "44000", "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto sans Adresse codePostal (null) exception levée
            """)
    @Test
    void testAjouterSansAdressCodePostal() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("sdfs", null, "Nantes"),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto sans Adresse ville (null) exception levée
            """)
    @Test
    void testAjouterSansAdressVille() {
        ClientRequestDto dto = new ClientRequestDto("L", "V", "v@gmail.fr", "ocre", new AdresseDto("sdfs", "44000", null),
                LocalDate.of(1990, 12, 24),
                List.of(Permis.A), true);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
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

        return new ClientResponseDto("L", "V", "v@gmail.fr", "ocre",
                new Adresse(1L, "29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24), List.of(Permis.A), true);
    }

    private static ClientResponseDto creeClientResponseDto2() {

        return new ClientResponseDto("Ldp", "A", "a@gmail.fr", "test",
                new Adresse(2L, "29bis rue de la bibine", "44100", "Saint-Herblain"),
                LocalDate.of(1992, 12, 24), List.of(Permis.A), true);
    }


}