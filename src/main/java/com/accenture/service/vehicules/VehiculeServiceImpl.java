package com.accenture.service.vehicules;

import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.repository.VehiculeDao;
import com.accenture.repository.entity.vehicules.Moto;
import com.accenture.repository.entity.vehicules.Vehicule;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.mapper.vehicules.VehiculeMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeDao vehiculeDao;
    private final VehiculeMapper vehiculeMapper;

    public VehiculeServiceImpl(VehiculeDao vehiculeDao, VehiculeMapper vehiculeMapper) {
        this.vehiculeDao = vehiculeDao;
        this.vehiculeMapper = vehiculeMapper;
    }


    @Override
    public List<Record> trouverVehicules() {
        return vehiculeDao
                .findAll()
                .stream()
                .map(this::getVehicules)
                .toList();
    }

    @Override
    public List<Record> tousActif(Boolean actif) {
        return vehiculeDao
                .findAll()
                .stream()
                .filter(vehicule -> vehicule.getActif().equals(actif))
                .map(this::getVehicules)
                .toList();
    }

    @Override
    public List<Record> retirerDuParc(Boolean retirerDuParc) {
        return vehiculeDao
                .findAll()
                .stream()
                .filter(vehicule -> vehicule.getRetirerDuParc().equals(retirerDuParc))
                .map(this::getVehicules)
                .toList();
    }

    private Record getVehicules(Vehicule vehicule) {
        if (vehicule instanceof Voiture voiture) {
            return vehiculeMapper.toVoitureResponseDto(voiture);
        } else if (vehicule instanceof Moto moto) {
            return vehiculeMapper.toMotoResponseDto(moto);
        }
        throw new VehiculeException("Aucun vehicules trouvé");
    }
}
