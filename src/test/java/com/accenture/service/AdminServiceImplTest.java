package com.accenture.service;

import com.accenture.model.Fonction;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.Admin;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    AdminDao daoMock;

    @Mock
    AdminMapper mapperMock;

    @InjectMocks
    AdminServiceImpl service;

    @DisplayName("""
            Test de la methode trouver (String mail) qui doit renvoyer une exception lorsque l'admin existe pas
            """)

    @Test
    void testTrouverExistePas() {
        Mockito.when(daoMock.findById("a@gmail.fr")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver("a@gmail.fr"));
        assertEquals("Je n'ai pas trouvé l'email", ex.getMessage());
    }

    @DisplayName("""
            Test de la méthode trouver (String email) qui doit renvoyer un ClientResponseDto lorsque le client existe ne base")
            
            """)

    @Test
    void testTrouverExiste() {
        Admin a = creeAdmin();
        Optional<Admin> optAdmin = Optional.of(a);
        Mockito.when(daoMock.findById("admin@gmail.fr")).thenReturn(optAdmin);

        AdminResponseDto dto = creeAdminResponseDto();
        Mockito.when(mapperMock.toAdminResponseDto(a)).thenReturn(dto);
        assertSame(dto, service.trouver("admin@gmail.fr"));


    }


    @DisplayName("""
            Test methode ajouter
            """)
    @Test
    void testAjouterOk(){
        AdminRequestDto requestDto = new AdminRequestDto("dmin","A","admin@gmail.fr","a",Fonction.ADMIN);
        Admin adminAvantEnreg = creeAdmin();
        adminAvantEnreg.setEmail("admin@gmail.fr");

        Admin adminApresEnreg = creeAdmin();
        AdminResponseDto responseDto = creeAdminResponseDto();

        Mockito.when(mapperMock.toAdmin(requestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(daoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(mapperMock.toAdminResponseDto(adminApresEnreg)).thenReturn(responseDto);

        assertSame(responseDto, service.ajouter(requestDto));
        Mockito.verify(daoMock, Mockito.times(1)).save(adminAvantEnreg);


    }


@DisplayName("""
        Test de la methode trouverToutes qui doit renvoyer une list de AdminResponseDto correspondant aux utilisateur existant en bdd
        """)
@Test
void testTrouverToutes(){
        Admin admin1 = creeAdmin();
        Admin admin2 = creeAdmin2();

    List<Admin> admins = List.of(admin1, admin2);

    AdminResponseDto adminResponseDto1 = creeAdminResponseDto();
    AdminResponseDto adminResponseDto2 = creeAdminResponseDto2();

    List<AdminResponseDto> dtos = List.of(adminResponseDto1, adminResponseDto2);

    Mockito.when(daoMock.findAll()).thenReturn(admins);
    Mockito.when(mapperMock.toAdminResponseDto(admin1)).thenReturn(adminResponseDto1);
    Mockito.when(mapperMock.toAdminResponseDto(admin2)).thenReturn(adminResponseDto2);
 //   Mockito.when((mapperMock.toAdminResponseDto(Mockito.any()))).thenReturn(adminResponseDto1, adminResponseDto2);


    List<AdminResponseDto> toutes = service.trouverToutes();
    assertEquals(dtos, toutes);


}





    private static Admin creeAdmin() {
        Admin a = new Admin();
        a.setNom("dmin");
        a.setPrenom("A");
        a.setEmail("admin@gmail.fr");
        a.setFonction(Fonction.ADMIN);
        a.setPassword("a");
        return a;
    }


    private static Admin creeAdmin2(){
        Admin a = new Admin();
        a.setNom("MAN");
        a.setPrenom("SUPER");
        a.setEmail("superman@gmail.fr");
        a.setFonction(Fonction.ADMIN);
        a.setPassword("b");
        return a;
    }


    private static AdminResponseDto creeAdminResponseDto() {
        return new AdminResponseDto("dmin", "A", "admin@gmail.fr", "a", Fonction.ADMIN);
    }

    private static AdminResponseDto creeAdminResponseDto2(){
        return new AdminResponseDto("MAN","SUPER","superman@gmail.fr","b",Fonction.ADMIN);
    }

}