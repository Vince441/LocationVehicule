package com.accenture.service.vehicules;

import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.repository.VoitureDao;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.vehicules.VoitureRequestDto;
import com.accenture.service.dto.vehicules.VoitureResponseDto;
import com.accenture.service.mapper.vehicules.VoitureMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoitureServiceImpl implements VoitureService {

    public static final String JE_N_AI_PAS_TROUVER_L_ID = "Je n'ai pas touvé l'ID";
    private final VoitureDao voitureDao;
    private final VoitureMapper voitureMapper;

    public VoitureServiceImpl(VoitureDao voitureDao, VoitureMapper voitureMapper) {
        this.voitureDao = voitureDao;
        this.voitureMapper = voitureMapper;
    }


    @Override
    public List<VoitureResponseDto> trouverToute() {
        return voitureDao
                .findAll()
                .stream()
                .map(voitureMapper::toVoitureResponseDto)
                .toList();
    }


    @Override
    public VoitureResponseDto ajouter(VoitureRequestDto voitureRequestDto) {
        verifierVoiture(voitureRequestDto);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
        Voiture voiturEnreg = voitureDao.save(voiture);

        return voitureMapper.toVoitureResponseDto(voiturEnreg);
    }

    @Override
    public List<VoitureResponseDto> trouverActif(Boolean actif) {
        List<Voiture> listVoiture = voitureDao.findAll();

        return listVoiture
                .stream()
                .filter(voiture -> voiture.getActif().equals(actif))
                .map(voitureMapper::toVoitureResponseDto)
                .toList();
    }


    @Override
    public VoitureResponseDto trouverUneVoiture(int id) {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if (optVoiture.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);
        Voiture voiture = optVoiture.get();
        return voitureMapper.toVoitureResponseDto(voiture);
    }

    @Override
    public VoitureResponseDto modifier(int id, VoitureRequestDto voitureRequestDto) throws VehiculeException {
        if (!voitureDao.existsById(id))
            throw new VehiculeException(JE_N_AI_PAS_TROUVER_L_ID);
        verifierVoiture(voitureRequestDto);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
        voiture.setId(id);
        Voiture voitureEnreg = voitureDao.save(voiture);
        return voitureMapper.toVoitureResponseDto(voitureEnreg);
    }

    @Override
    public void supprimer(int id) throws VehiculeException {
        if (voitureDao.existsById(id))
            voitureDao.deleteById(id);
        else
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);
    }


    private static void verifierVoiture(VoitureRequestDto voitureRequestDto) throws VehiculeException {
        if (voitureRequestDto == null)
            throw new VehiculeException("La voiture est null");
        if (voitureRequestDto.marque() == null || voitureRequestDto.marque().isBlank())
            throw new VehiculeException("La marque est obligatoire");
        if (voitureRequestDto.modele() == null || voitureRequestDto.modele().isBlank())
            throw new VehiculeException("Le modele est obligatoire");
        if (voitureRequestDto.couleur() == null || voitureRequestDto.couleur().isBlank())
            throw new VehiculeException("La couleur est obligatoire");
        if (voitureRequestDto.permis() == null)
            throw new VehiculeException("Le permis est obligatoire");
        if (voitureRequestDto.nombrePlace() == null || voitureRequestDto.nombrePlace() < 2)
            throw new VehiculeException("Le nombre de place est obligatoire et supperieur à 2");
        if (voitureRequestDto.carburant() == null)
            throw new VehiculeException("Le type de carburant est obligatoire");
        if (voitureRequestDto.nombreDePorte() == null || voitureRequestDto.nombreDePorte() != 3 && voitureRequestDto.nombreDePorte() != 5)
            throw new VehiculeException("Le nombre de porte est obligatoire et de 3 ou 5 portes");
        if (voitureRequestDto.transmission() == null)
            throw new VehiculeException("Le type de transmission est obligatoire");
        if (voitureRequestDto.nombreDeBagage() == null || voitureRequestDto.nombreDeBagage() <= 0)
            throw new VehiculeException("Le nombre de bagage est obligatoire et supperieur à 0");
        if (voitureRequestDto.tarifJournalier() == null || voitureRequestDto.tarifJournalier() <= 0)
            throw new VehiculeException("Le tarif journalier est obligatoire et supperieur à 0");
        if (voitureRequestDto.kilometrage() == null || voitureRequestDto.kilometrage() <= 0)
            throw new VehiculeException("Le kilometrage est obligatoire et supp à 0");

    }
}
