package com.accenture.service.vehicules;

import com.accenture.model.Permis;
import com.accenture.model.vehicule.Transmission;
import com.accenture.model.vehicule.TypeCarburant;
import com.accenture.repository.VoitureDao;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.vehicules.VoitureRequestDto;
import com.accenture.service.dto.vehicules.VoitureResponseDto;
import com.accenture.service.mapper.vehicules.VoitureMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class VoitureServiceImplTest {

    @Mock
    VoitureDao daoMock;

    @Mock
    VoitureMapper mapperMock;

    @InjectMocks
    VoitureServiceImpl service;

    @DisplayName("""
            Test de la méthode trouverToutes qui doit renoyer une liste de VoitureResponseDto qui existe en base de donnée
            """)

    @Test
    void testTrouverToutes(){
        Voiture voiture1 = creeVoiture1();
        Voiture voiture2 = creeVoiture2();

        List<Voiture> voitures = List.of(voiture1, voiture2);

        VoitureResponseDto voitureResponseDtoVoiture1 = creeVoitureResponseDto();
        VoitureResponseDto voitureResponseDtoVoiture2 = creeVoitureResponseDto2();

        List<VoitureResponseDto> dtos = List.of(voitureResponseDtoVoiture1, voitureResponseDtoVoiture2);

        Mockito.when(daoMock.findAll()).thenReturn(voitures);
        Mockito.when(mapperMock.toVoitureResponseDto(voiture1)).thenReturn(voitureResponseDtoVoiture1);
        Mockito.when(mapperMock.toVoitureResponseDto(voiture2)).thenReturn(voitureResponseDtoVoiture2);

        List<VoitureResponseDto> listVoitureDto = service.trouverToute();
        assertEquals(dtos, listVoitureDto);


    }

    @DisplayName("""
            Test methode ajouter
            """)
    @Test
    void testAjouterOk(){

        VoitureRequestDto requestDto = new VoitureRequestDto("Ford", "Mustang","Noir", Permis.A,
                5,TypeCarburant.ESSENCE,3,Transmission.MANUELLE,
                true,20, 200,0,true, false);
Voiture voitureAvantEnreg = creeVoiture1();
Voiture voitureApresEnreg = creeVoiture1();

VoitureResponseDto responseDto = creeVoitureResponseDto();

Mockito.when(mapperMock.toVoiture(requestDto)).thenReturn(voitureAvantEnreg);
Mockito.when(daoMock.save(voitureAvantEnreg)).thenReturn(voitureApresEnreg);
Mockito.when(mapperMock.toVoitureResponseDto(voitureApresEnreg)).thenReturn(responseDto);

assertSame(responseDto, service.ajouter(requestDto));


    }

    private static Voiture creeVoiture1(){
        Voiture v = new Voiture();
        v.setMarque("Ford");
        v.setModele("Mustang");
        v.setCouleur("Noir");
        v.setPermis(Permis.A);
        v.setCarburant(TypeCarburant.ESSENCE);
        v.setNombreDePorte(3);
        v.setNombrePlace(5);
        v.setTransmission(Transmission.AUTO);
        v.setClimatisation(true);
        v.setNombreDeBagage(20);

        v.setTarifJournalier(200);
        v.setKilometrage(0);
        v.setActif(true);
        v.setRetirerDuParc(false);
return v;
    }

    private static Voiture creeVoiture2(){
        Voiture v = new Voiture();
        v.setMarque("Peugeot");
        v.setModele("207");
        v.setCouleur("Noir");
        v.setPermis(Permis.A);
        v.setCarburant(TypeCarburant.ESSENCE);
        v.setNombreDePorte(5);
        v.setNombrePlace(5);
        v.setTransmission(Transmission.MANUELLE);
        v.setClimatisation(false);
        v.setNombreDeBagage(10);

        v.setTarifJournalier(80);
        v.setKilometrage(120000);
        v.setActif(true);
        v.setRetirerDuParc(false);
        return v;
    }

private static VoitureResponseDto creeVoitureResponseDto(){
        return new VoitureResponseDto("Ford", "Mustang","Noir", Permis.A,
                5,TypeCarburant.ESSENCE,3,Transmission.MANUELLE,
                true,20, 200,0,true, false);
}

private static VoitureResponseDto creeVoitureResponseDto2(){
        return new VoitureResponseDto("Peugeot", "207","Noir",Permis.A,
                5,TypeCarburant.ESSENCE,5,Transmission.MANUELLE,
                false,10,80,120000, true, false);
}


}