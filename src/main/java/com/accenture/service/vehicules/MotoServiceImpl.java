package com.accenture.service.vehicules;

import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.repository.MotoDao;
import com.accenture.repository.entity.vehicules.Moto;
import com.accenture.service.dto.vehicules.MotoRequestDto;
import com.accenture.service.dto.vehicules.MotoResponseDto;
import com.accenture.service.mapper.vehicules.MotoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoServiceImpl implements MotoService {

    public static final String JE_N_AI_PAS_TROUVER_L_ID = "Je n'ai pas touvé l'ID";
    private final MotoDao motoDao;
    private final MotoMapper motoMapper;

    public MotoServiceImpl(MotoDao motoDao, MotoMapper motoMapper) {
        this.motoDao = motoDao;
        this.motoMapper = motoMapper;
    }


    @Override
    public List<MotoResponseDto> trouverActif(Boolean actif) {
        List<Moto> listMoto = motoDao.findAll();
        return listMoto
                .stream()
                .filter(voiture -> voiture.getActif().equals(actif))
                .map(motoMapper::toMotoResponseDto)
                .toList();
    }

    @Override
    public List<MotoResponseDto> trouverToute() {
        return motoDao
                .findAll()
                .stream()
                .map(motoMapper::toMotoResponseDto)
                .toList();
    }

    @Override
    public MotoResponseDto ajouter(MotoRequestDto motoRequestDto) {
verifierMoto(motoRequestDto);
Moto moto = motoMapper.toMoto(motoRequestDto);
Moto motoEnreg = motoDao.save(moto);
return motoMapper.toMotoResponseDto(motoEnreg);

    }

    @Override
    public MotoResponseDto trouverUneMoto(int id) {
        Optional<Moto> optMoto = motoDao.findById(id);
        if(optMoto.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);
        Moto moto = optMoto.get();
        return motoMapper.toMotoResponseDto(moto);
    }

    @Override
    public MotoResponseDto modifier(int id, MotoRequestDto motoRequestDto)  throws VehiculeException{
        if(!motoDao.existsById(id))
            throw new VehiculeException(JE_N_AI_PAS_TROUVER_L_ID);
        verifierMoto(motoRequestDto);
        Moto moto = motoMapper.toMoto(motoRequestDto);
        moto.setId(id);
        Moto motoEnreg = motoDao.save(moto);
        return motoMapper.toMotoResponseDto(motoEnreg);
    }

    @Override
    public void supprimer(int id) throws VehiculeException {
        if(motoDao.existsById(id))
            motoDao.deleteById(id);
        else throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);
    }


    private static void verifierMoto(MotoRequestDto motoRequestDto) throws VehiculeException {
        if (motoRequestDto == null)
            throw new VehiculeException("La voiture est null");
        if (motoRequestDto.marque() == null || motoRequestDto.marque().isBlank())
            throw new VehiculeException("La marque est obligatoire");
        if (motoRequestDto.modele() == null || motoRequestDto.modele().isBlank())
            throw new VehiculeException("Le modele est obligatoire");
        if (motoRequestDto.couleur() == null || motoRequestDto.couleur().isBlank())
            throw new VehiculeException("La couleur est obligatoire");
        if (motoRequestDto.permis() == null)
            throw new VehiculeException("Le permis est obligatoire");
        if (motoRequestDto.poid() == null || motoRequestDto.poid() == 0)
            throw new VehiculeException("Le poid est obligatoire et supperieur à 0");
        if (motoRequestDto.tarifJournalier() == null || motoRequestDto.tarifJournalier() == 0)
            throw new VehiculeException("Le tarif journalier est obligatoire et supperieur à 0");
        if (motoRequestDto.transmission() == null)
            throw new VehiculeException("Le type de transmission est obligatoire");
        if (motoRequestDto.nombreDeCylindre() == null || motoRequestDto.nombreDeCylindre() == 0)
            throw new VehiculeException("Le nombre de cylindre est obligatoire");
        if (motoRequestDto.cylindree() == null)
            throw new VehiculeException("Le nombre d'exception est obligatoire");
        if (motoRequestDto.puissanceKw() == null || motoRequestDto.puissanceKw() == 0)
            throw new VehiculeException("La puissance est obligatoire et supp à 0");
        if (motoRequestDto.kilometrage() == null || motoRequestDto.kilometrage() <= 0)
            throw new VehiculeException("Le kilometrage est obligatoire et supp à 0");

    }

    }



