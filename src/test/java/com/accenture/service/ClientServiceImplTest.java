package com.accenture.service;

import com.accenture.repository.ClientDao;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {


    @Mock
    ClientDao daoMock;

    @Mock
    ClientMapper mapperMock;

    @InjectMocks
    ClientServiceImpl service;


    @DisplayName("""
            Test de la méthode trouver (int id) qui doit renvoyer une exception lorsque la tache n'existe pas en bas;
            """)
    @Test
    void testTrouverExistePas(){
        Mockito.when(daoMock.findById(10L)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver(10L));
        assertEquals("Je n'ai pas trouvé l'id", ex.getMessage());

    }

    @DisplayName("""
            Test de la méthode trouver (int id) qui doit renvoyer un ClientResponseDto lorsque
            """)
    

}