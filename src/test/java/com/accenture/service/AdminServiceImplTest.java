package com.accenture.service;

import com.accenture.model.Fonction;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.Admin;
import com.accenture.service.dto.AdminResponseDto;
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


    private static Admin creeAdmin() {
        Admin a = new Admin();
        a.setNom("dmin");
        a.setPrenom("A");
        a.setEmail("admin@gmail.fr");
        a.setFonction(Fonction.ADMIN);
        a.setPassword("a");
        return a;
    }
}

private static AdminResponseDto creeAdminResponseDto() {
    return new AdminResponseDto
            ("dmin", "A", "admin@gmail.fr", "a", Fonction.ADMIN);
}