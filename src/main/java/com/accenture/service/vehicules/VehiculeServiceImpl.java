package com.accenture.service.vehicules;

import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.repository.VehiculeDao;
import com.accenture.repository.entity.vehicules.Moto;
import com.accenture.repository.entity.vehicules.Vehicule;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.vehicules.MotoResponseDto;
import com.accenture.service.dto.vehicules.VoitureResponseDto;
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


    /**
     * Récupère tous les véhicules présents dans la base de données et les transforme en une liste d'objets {@link Record}.
     *
     * @return une liste de {@link Record} représentant tous les véhicules. La liste peut être vide si aucun véhicule n'est trouvé dans la base de données.
     */
    @Override
    public List<Record> trouverVehicules() {
        return vehiculeDao.findAll().stream().map(this::getVehicules).toList();
    }

    /**
     * Récupère tous les véhicules ayant un état d'activité correspondant à la valeur spécifiée et les transforme en une liste d'objets {@link Record}.
     *
     * @param actif un boolean qui détermine l'état d'activité des véhicules à récupérer.
     *              Si true, seuls les véhicules actifs sont retournés, sinon, seuls les véhicules inactifs sont récupérés.
     * @return une liste de {@link Record} représentant les véhicules filtrés par leur état d'activité.
     * La liste peut être vide si aucun véhicule ne correspond aux critères.
     */
    @Override
    public List<Record> tousActif(Boolean actif) {
        return vehiculeDao.findAll().stream().filter(vehicule -> vehicule.getActif().equals(actif)).map(this::getVehicules).toList();
    }

    /**
     * Récupère tous les véhicules ayant un état correspondant à la valeur spécifiée pour le retrait du parc et les transforme en une liste d'objets {@link Record}.
     *
     * @param retirerDuParc un boolean qui détermine si les véhicules doivent être retirés du parc ou non.
     *                      Si true, seuls les véhicules retirés du parc sont retournés, sinon, seuls les véhicules non retirés sont récupérés.
     * @return une liste de {@link Record} représentant les véhicules filtrés par leur état de retrait du parc.
     * La liste peut être vide si aucun véhicule ne correspond aux critères.
     */
    @Override
    public List<Record> retirerDuParc(Boolean retirerDuParc) {
        return vehiculeDao.findAll().stream().filter(vehicule -> vehicule.getRetirerDuParc().equals(retirerDuParc)).map(this::getVehicules).toList();
    }

    /**
     * Transforme un objet {@link Vehicule} en un objet {@link Record} approprié en fonction de son type (Voiture ou Moto).
     *
     * @param vehicule l'objet {@link Vehicule} à transformer. Il peut être une instance de {@link Voiture} ou {@link Moto}.
     * @return un objet de type {@link Record} correspondant à l'objet {@link Vehicule} fourni, soit une {@link VoitureResponseDto}
     * si le véhicule est une instance de {@link Voiture}, soit une {@link MotoResponseDto} si le véhicule est une instance de {@link Moto}.
     * @throws VehiculeException si l'objet {@link Vehicule} fourni n'est ni une instance de {@link Voiture} ni une instance de {@link Moto}.
     */
    private Record getVehicules(Vehicule vehicule) {
        if (vehicule instanceof Voiture voiture) {
            return vehiculeMapper.toVoitureResponseDto(voiture);
        } else if (vehicule instanceof Moto moto) {
            return vehiculeMapper.toMotoResponseDto(moto);
        }
        throw new VehiculeException("Aucun vehicules trouvé");
    }
}
