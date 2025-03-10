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


    /**
     * Récupère toutes les voitures présentes dans la base de données et les transforme en une liste d'objets {@link VoitureResponseDto}.
     *
     * @return une liste de {@link VoitureResponseDto} représentant toutes les voitures. La liste peut être vide si aucune voiture
     * n'est trouvée dans la base de données.
     */
    @Override
    public List<VoitureResponseDto> trouverToute() throws VehiculeException {
        return voitureDao.findAll().stream().map(voitureMapper::toVoitureResponseDto).toList();
    }

    /**
     * Ajoute une nouvelle voiture dans la base de données après avoir validé les informations fournies.
     *
     * @param voitureRequestDto un objet de type {@link VoitureRequestDto} contenant les informations nécessaires pour créer la voiture.
     * @return un objet de type {@link VoitureResponseDto} représentant la voiture ajoutée, avec ses données persistées dans la base de données.
     * @throws IllegalArgumentException si les données de la voiture ne sont pas valides selon les critères définis dans la méthode {@link #verifierVoiture(VoitureRequestDto)}.
     */
    @Override
    public VoitureResponseDto ajouter(VoitureRequestDto voitureRequestDto) throws VehiculeException {
        verifierVoiture(voitureRequestDto);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
        Voiture voiturEnreg = voitureDao.save(voiture);

        return voitureMapper.toVoitureResponseDto(voiturEnreg);
    }

    private static void verifierVoiture(VoitureRequestDto voitureRequestDto) throws VehiculeException {
        verifierInformationVehicule(voitureRequestDto);
        verifierInformationVoiture(voitureRequestDto);
    }

    private static void verifierInformationVoiture(VoitureRequestDto voitureRequestDto) {
        if (voitureRequestDto.nombrePlace() == null || voitureRequestDto.nombrePlace() < 2)
            throw new VehiculeException("Le nombre de place est obligatoire et supperieur à 2");
        if (voitureRequestDto.carburant() == null) throw new VehiculeException("Le type de carburant est obligatoire");
        if (voitureRequestDto.nombreDePorte() == null || voitureRequestDto.nombreDePorte() != 3 && voitureRequestDto.nombreDePorte() != 5)
            throw new VehiculeException("Le nombre de porte est obligatoire et de 3 ou 5 portes");
        if (voitureRequestDto.transmission() == null)
            throw new VehiculeException("Le type de transmission est obligatoire");
        if (voitureRequestDto.nombreDeBagage() == null || voitureRequestDto.nombreDeBagage() <= 0)
            throw new VehiculeException("Le nombre de bagage est obligatoire et supperieur à 0");
    }

    private static void verifierInformationVehicule(VoitureRequestDto voitureRequestDto) {
        if (voitureRequestDto == null) throw new VehiculeException("La voiture est null");
        if (voitureRequestDto.marque() == null || voitureRequestDto.marque().isBlank())
            throw new VehiculeException("La marque est obligatoire");
        if (voitureRequestDto.modele() == null || voitureRequestDto.modele().isBlank())
            throw new VehiculeException("Le modele est obligatoire");
        if (voitureRequestDto.couleur() == null || voitureRequestDto.couleur().isBlank())
            throw new VehiculeException("La couleur est obligatoire");
        if (voitureRequestDto.permis() == null) throw new VehiculeException("Le permis est obligatoire");
        if (voitureRequestDto.tarifJournalier() == null || voitureRequestDto.tarifJournalier() <= 0)
            throw new VehiculeException("Le tarif journalier est obligatoire et supperieur à 0");
        if (voitureRequestDto.kilometrage() == null || voitureRequestDto.kilometrage() <= 0)
            throw new VehiculeException("Le kilometrage est obligatoire et supp à 0");
    }


    /**
     * Récupère toutes les voitures qui ont un état d'activité correspondant à la valeur spécifiée et les transforme en une liste d'objets {@link VoitureResponseDto}.
     *
     * @param actif un boolean qui détermine l'état d'activité des voitures à récupérer.
     *              Si true, seuls les véhicules actifs sont retournés, sinon, seuls les véhicules inactifs sont récupérés.
     * @return une liste de {@link VoitureResponseDto} représentant les voitures filtrées par leur état d'activité.
     * La liste peut être vide si aucune voiture ne correspond aux critères.
     */
    @Override
    public List<VoitureResponseDto> trouverActif(Boolean actif) throws VehiculeException {
        List<Voiture> listVoiture = voitureDao.findAll();

        return listVoiture.stream().filter(voiture -> voiture.getActif().equals(actif)).map(voitureMapper::toVoitureResponseDto).toList();
    }


    /**
     * Récupère une voiture par son identifiant et la transforme en un objet {@link VoitureResponseDto}.
     *
     * @param id l'identifiant de la voiture à récupérer.
     * @return un objet {@link VoitureResponseDto} représentant la voiture trouvée, ou une exception {@link EntityNotFoundException} si la voiture n'est pas trouvée.
     * @throws EntityNotFoundException si aucune voiture n'est trouvée pour l'identifiant spécifié.
     */
    @Override
    public VoitureResponseDto trouverUneVoiture(int id) throws VehiculeException {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if (optVoiture.isEmpty()) throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);
        Voiture voiture = optVoiture.get();
        return voitureMapper.toVoitureResponseDto(voiture);
    }


    /**
     * Modifies an existing car (vehicle) partially based on the provided data.
     *
     * This method takes in a vehicle ID and a {@link VoitureRequestDto} object containing
     * the new values for the vehicle fields. It retrieves the existing vehicle from the database,
     * updates its fields based on the new data, and then saves the updated vehicle back to the database.
     *
     *
     * @param id                the ID of the vehicle to be modified
     * @param voitureRequestDto the data transfer object containing the new values to be applied to the vehicle
     * @return a {@link VoitureResponseDto} containing the updated vehicle data
     * @throws VehiculeException       if there is an issue with the vehicle data or processing
     * @throws EntityNotFoundException if the vehicle with the given ID is not found in the database
     */
    @Override
    public VoitureResponseDto modifierPartiellement(int id, VoitureRequestDto voitureRequestDto) throws VehiculeException {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if (optVoiture.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);

        Voiture voitureExistant = optVoiture.get();
        Voiture voitureEnreg = voitureMapper.toVoiture(voitureRequestDto);


        remplacer(voitureExistant, voitureEnreg);

        Voiture modifVoiture = voitureDao.save(voitureExistant);
        return voitureMapper.toVoitureResponseDto(modifVoiture);

    }


    private static void remplacer(Voiture voitureExistant, Voiture voitureEnreg) throws VehiculeException {
        remplacerInformationVehicule(voitureExistant, voitureEnreg);
        remplacerInformationVoiture(voitureExistant, voitureEnreg);
    }

    private static void remplacerInformationVoiture(Voiture voitureExistant, Voiture voitureEnreg) {
        if (voitureEnreg.getNombrePlace() != null)
            voitureExistant.setNombrePlace(voitureEnreg.getNombrePlace());
        if (voitureEnreg.getCarburant() != null)
            voitureExistant.setCarburant(voitureEnreg.getCarburant());
        if (voitureEnreg.getClimatisation() != null)
            voitureExistant.setClimatisation(voitureEnreg.getClimatisation());
        if (voitureEnreg.getNombreDeBagage() != null)
            voitureExistant.setNombreDeBagage(voitureEnreg.getNombreDeBagage());
        if (voitureEnreg.getTypeVoiture() != null)
            voitureExistant.setTypeVoiture(voitureEnreg.getTypeVoiture());
    }

    private static void remplacerInformationVehicule(Voiture voitureExistant, Voiture voitureEnreg) {
        if (voitureEnreg.getMarque() != null)
            voitureExistant.setMarque(voitureEnreg.getMarque());
        if (voitureEnreg.getModele() != null)
            voitureExistant.setModele(voitureEnreg.getModele());
        if (voitureEnreg.getCouleur() != null)
            voitureExistant.setCouleur(voitureEnreg.getCouleur());
        if (voitureEnreg.getPermis() != null)
            voitureExistant.setPermis(voitureEnreg.getPermis());
        if (voitureEnreg.getTarifJournalier() != null)
            voitureExistant.setTarifJournalier(voitureEnreg.getTarifJournalier());
        if (voitureEnreg.getKilometrage() != null)
            voitureExistant.setKilometrage(voitureEnreg.getKilometrage());
        if (voitureEnreg.getActif() != null)
            voitureExistant.setActif(voitureEnreg.getActif());
        if (voitureEnreg.getRetirerDuParc() != null)
            voitureExistant.setRetirerDuParc(voitureEnreg.getRetirerDuParc());
    }


    /**
     * Supprime une voiture de la base de données en fonction de son identifiant.
     *
     * @param id l'identifiant de la voiture à supprimer.
     * @throws VehiculeException       si la voiture n'existe pas dans la base de données pour l'identifiant spécifié.
     * @throws EntityNotFoundException si aucune voiture n'est trouvée pour l'identifiant fourni.
     */
    @Override
    public void supprimer(int id) throws VehiculeException {
        if (voitureDao.existsById(id)) voitureDao.deleteById(id);
        else throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);
    }


}
