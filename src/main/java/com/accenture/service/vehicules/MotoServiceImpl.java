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

    /**
     * Trouve toutes les motos dont l'état d'activité correspond à la valeur spécifiée.
     *
     * @param actif un boolean qui détermine l'état d'activité des motos à rechercher.
     *              Si true, seules les motos actives seront retournées, sinon, ce seront les motos inactives.
     * @return une liste de {@link MotoResponseDto} représentant les motos dont l'état d'activité correspond à la valeur spécifiée.
     * Cette liste peut être vide si aucune moto ne correspond aux critères.
     */
    @Override
    public List<MotoResponseDto> trouverActif(Boolean actif) {
        List<Moto> listMoto = motoDao.findAll();
        return listMoto.stream().filter(voiture -> voiture.getActif().equals(actif)).map(motoMapper::toMotoResponseDto).toList();
    }


    /**
     * Récupère toutes les motos présentes dans la base de données et les transforme en {@link MotoResponseDto}.
     *
     * @return une liste de {@link MotoResponseDto} représentant toutes les motos. La liste peut être vide si aucune moto
     * n'est présente dans la base de données.
     */
    @Override
    public List<MotoResponseDto> trouverToute() {
        return motoDao.findAll().stream().map(motoMapper::toMotoResponseDto).toList();
    }

    /**
     * Ajoute une nouvelle moto dans la base de données après avoir validé les informations fournies.
     *
     * @param motoRequestDto l'objet contenant les informations de la moto à ajouter.
     *                       Il s'agit d'un objet de type {@link MotoRequestDto} qui contient les données nécessaires pour créer la moto.
     * @return un objet de type {@link MotoResponseDto} représentant la moto ajoutée, avec ses données persistées dans la base de données.
     * @throws IllegalArgumentException si les données de la moto ne sont pas valides selon les critères définis dans la méthode {@link #verifierMoto(MotoRequestDto)}.
     */
    @Override
    public MotoResponseDto ajouter(MotoRequestDto motoRequestDto) throws VehiculeException {
        verifierMoto(motoRequestDto);
        Moto moto = motoMapper.toMoto(motoRequestDto);
        Moto motoEnreg = motoDao.save(moto);
        return motoMapper.toMotoResponseDto(motoEnreg);

    }


    /**
     * Recherche une moto dans la base de données à partir de son identifiant.
     *
     * @param id l'identifiant de la moto à rechercher.
     * @return un objet de type {@link MotoResponseDto} représentant la moto trouvée, ou une exception si la moto n'existe pas.
     * @throws EntityNotFoundException si aucune moto n'est trouvée pour l'identifiant spécifié.
     */
    @Override
    public MotoResponseDto trouverUneMoto(int id) {
        Optional<Moto> optMoto = motoDao.findById(id);
        if (optMoto.isEmpty()) throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);
        Moto moto = optMoto.get();
        return motoMapper.toMotoResponseDto(moto);
    }


    /**
     * Modifies an existing motorcycle (vehicle) partially based on the provided data.
     * <p>
     * This method takes in a motorcycle ID and a {@link MotoRequestDto} object containing
     * the new values for the motorcycle fields. It retrieves the existing motorcycle from the database,
     * updates its fields based on the new data, and then saves the updated motorcycle back to the database.
     * </p>
     *
     * @param id             the ID of the motorcycle to be modified
     * @param motoRequestDto the data transfer object containing the new values to be applied to the motorcycle
     * @return a {@link MotoResponseDto} containing the updated motorcycle data
     * @throws VehiculeException       if there is an issue with the motorcycle data or processing
     * @throws EntityNotFoundException if the motorcycle with the given ID is not found in the database
     */

    @Override
    public MotoResponseDto modifierPartiellement(int id, MotoRequestDto motoRequestDto) throws VehiculeException {
        Optional<Moto> optMoto = motoDao.findById(id);
        if (optMoto.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);

        Moto motoExistant = optMoto.get();
        Moto motoEnreg = motoMapper.toMoto(motoRequestDto);

        remplacer(motoExistant, motoEnreg);

        Moto modifMoto = motoDao.save(motoExistant);
        return motoMapper.toMotoResponseDto(modifMoto);
    }

    private static void remplacer(Moto motoExistant, Moto motoEnreg) throws VehiculeException {
        remplacerInformationVehicule(motoExistant, motoEnreg);
        remplacerInformationMoto(motoExistant, motoEnreg);
    }

    private static void remplacerInformationMoto(Moto motoExistant, Moto motoEnreg) {
        if (motoEnreg.getPoid() != null)
            motoExistant.setPoid(motoEnreg.getPoid());
        if (motoEnreg.getCylindree() != null)
            motoExistant.setCylindree(motoEnreg.getCylindree());
        if (motoEnreg.getNombreDeCylindre() != null)
            motoExistant.setNombreDeCylindre(motoEnreg.getNombreDeCylindre());
        if (motoEnreg.getPuissanceKw() != null)
            motoExistant.setPuissanceKw(motoEnreg.getPuissanceKw());
        if (motoEnreg.getHauteurDeSelle() != null)
            motoExistant.setHauteurDeSelle(motoEnreg.getHauteurDeSelle());
        if (motoEnreg.getTypeMoto() != null)
            motoExistant.setTypeMoto(motoEnreg.getTypeMoto());
    }

    private static void remplacerInformationVehicule(Moto motoExistant, Moto motoEnreg) {
        if (motoEnreg.getMarque() != null)
            motoExistant.setMarque(motoEnreg.getMarque());
        if (motoEnreg.getModele() != null)
            motoExistant.setModele(motoEnreg.getModele());
        if (motoEnreg.getCouleur() != null)
            motoExistant.setCouleur(motoEnreg.getCouleur());
        if (motoEnreg.getPermis() != null)
            motoExistant.setPermis(motoEnreg.getPermis());
        if (motoEnreg.getTarifJournalier() != null)
            motoExistant.setTarifJournalier(motoEnreg.getTarifJournalier());
        if (motoEnreg.getKilometrage() != null)
            motoExistant.setKilometrage(motoEnreg.getKilometrage());
        if (motoEnreg.getActif() != null)
            motoExistant.setActif(motoEnreg.getActif());
        if (motoEnreg.getRetirerDuParc() != null)
            motoExistant.setRetirerDuParc(motoEnreg.getRetirerDuParc());
    }


    /**
     * Supprime une moto de la base de données en fonction de son identifiant.
     *
     * @param id l'identifiant de la moto à supprimer.
     * @throws VehiculeException       si aucune moto n'est trouvée pour l'identifiant spécifié.
     * @throws EntityNotFoundException si la moto avec l'identifiant donné n'existe pas dans la base de données.
     */
    @Override
    public void supprimer(int id) throws VehiculeException {
        if (motoDao.existsById(id)) motoDao.deleteById(id);
        else throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_ID);
    }


    /**
     * Vérifie que toutes les informations d'une moto sont valides dans l'objet {@link MotoRequestDto}.
     * Cette méthode effectue une série de contrôles pour s'assurer que les champs requis sont présents et valides.
     * Si un champ est manquant ou invalide, une exception {@link VehiculeException} est lancée avec un message d'erreur approprié.
     *
     * @param motoRequestDto l'objet contenant les informations de la moto à vérifier.
     * @throws VehiculeException si l'un des champs requis est invalide ou manquant dans l'objet {@link MotoRequestDto}.
     */
    private static void verifierMoto(MotoRequestDto motoRequestDto) throws VehiculeException {
        verifierInformationVehicule(motoRequestDto);
        verifierInformationMoto(motoRequestDto);


    }

    private static void verifierInformationVehicule(MotoRequestDto motoRequestDto) {
        if (motoRequestDto == null) throw new VehiculeException("La voiture est null");
        if (motoRequestDto.marque() == null || motoRequestDto.marque().isBlank())
            throw new VehiculeException("La marque est obligatoire");
        if (motoRequestDto.modele() == null || motoRequestDto.modele().isBlank())
            throw new VehiculeException("Le modele est obligatoire");
        if (motoRequestDto.couleur() == null || motoRequestDto.couleur().isBlank())
            throw new VehiculeException("La couleur est obligatoire");
        if (motoRequestDto.permis() == null) throw new VehiculeException("Le permis est obligatoire");
        if (motoRequestDto.tarifJournalier() == null || motoRequestDto.tarifJournalier() == 0)
            throw new VehiculeException("Le tarif journalier est obligatoire et supperieur à 0");
        if (motoRequestDto.kilometrage() == null || motoRequestDto.kilometrage() <= 0)
            throw new VehiculeException("Le kilometrage est obligatoire et supp à 0");
    }

    private static void verifierInformationMoto(MotoRequestDto motoRequestDto) {
        if (motoRequestDto.transmission() == null)
            throw new VehiculeException("Le type de transmission est obligatoire");
        if (motoRequestDto.poid() == null || motoRequestDto.poid() == 0)
            throw new VehiculeException("Le poid est obligatoire et supperieur à 0");
        if (motoRequestDto.nombreDeCylindre() == null || motoRequestDto.nombreDeCylindre() == 0)
            throw new VehiculeException("Le nombre de cylindre est obligatoire");
        if (motoRequestDto.cylindree() == null) throw new VehiculeException("Le nombre d'exception est obligatoire");
        if (motoRequestDto.puissanceKw() == null || motoRequestDto.puissanceKw() == 0)
            throw new VehiculeException("La puissance est obligatoire et supp à 0");
    }

}



