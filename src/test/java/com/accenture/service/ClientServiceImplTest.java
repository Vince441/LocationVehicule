package com.accenture.service;

import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdresseDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.checkerframework.checker.units.qual.A;
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
    AdresseDto adresseDto;

    @Mock
    ClientMapper mapperMock;

    @InjectMocks
    ClientServiceImpl service;


    @DisplayName("""
            Test de la méthode trouver (int id) qui doit renvoyer une exception lorsque la tache n'existe pas en bas;
            """)
    @Test
    void testTrouverExistePas() {
        Mockito.when(daoMock.findById(10L)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver(10L));
        assertEquals("Je n'ai pas trouvé l'id", ex.getMessage());

    }

    @DisplayName("""
            Test de la méthode trouver (int id) qui doit renvoyer un ClientResponseDto lorsque le client existe en base
            """)
    @Test
    void testTrouverExiste() {

        Client c = creeClient();
        Optional<Client> optClient = Optional.of(c);
        Mockito.when(daoMock.findById(1L)).thenReturn(optClient);

        ClientResponseDto dto = creeClientResponseDto();
        Mockito.when(mapperMock.toClientResponseDto(c)).thenReturn(dto);
        assertSame(dto, service.trouver(1L));

    }

    //Toodoodoo

    @DisplayName("""
            Test de la methode trouverToutes qui doit renvoyer une liste de ClientResponseDto correspondant aux taches existant en vase de donnée
            """)
    private testTrouverToutLesCliens(){
        ClientResponseDto client1 = creeClientResponseDto();
                ClientResponseDto client2 = creeClientResponseDto2();

        List<ClientResponseDto> client = List.of(creeClientResponseDto(), creeClientResponseDto2());
        ClientResponseDto clientResponseDtoClient1 = creeClientResponseDto();
        ClientResponseDto clientResponseDtoClient2 = creeClientResponseDto2();

        List<ClientResponseDto> dtos = List.of(clientResponseDtoClient1, clientResponseDtoClient2);
        Mockito.when(daoMock.findAll()).thenReturn();
        Mockito.when(mapperMock.toClientResponseDto(creeClient())).thenReturn(creeClientResponseDto());
        Mockito.when(mapperMock.toClientResponseDto(client2)).thenReturn(creeClientResponseDto2());
        assertEquals(dtos, service.liste());

    }


    private static Client creeClient() {
        Client c = new Client();
        c.setId(1L);
        c.setNom("L");
        c.setPrenom("V");
        c.setEmail("a@gmail.fr");
        c.setPermis("A");
        c.setPassword("ocre");
        c.setAdresse(new Adresse(1L, "29 rue de la bibine", "44000", "Nantes"));
        c.setDateDeNaissance(LocalDate.of(1990, 12, 24));
        c.setDateInscription(LocalDate.of(2025, 2, 21));
        c.setDesactive(true);
        return c;
    }

    private static ClientResponseDto creeClientResponseDto() {

        return new ClientResponseDto(1L, "L", "V", "a@gmail.fr", "ocre",
                new Adresse(1L, "29 rue de la bibine", "44000", "Nantes"),
                LocalDate.of(1990, 12, 24), LocalDate.of(2025, 2, 21), "A", true);
    }

    private static ClientResponseDto creeClientResponseDto2() {

        return new ClientResponseDto(2L, "L", "A", "v@gmail.fr", "Agathe",
                new Adresse(2L, "30 rue de la bibine", "44100", "Saint-Herblain"),
                LocalDate.of(1992, 6, 10), LocalDate.of(2024, 3, 22), "A", true);
    }


}