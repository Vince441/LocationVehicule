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
    public MotoResponseDto ajouter(MotoRequestDto motoRequestDto) {
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
     * Modifie les informations d'une moto existante dans la base de données.
     *
     * @param id             l'identifiant de la moto à modifier.
     * @param motoRequestDto un objet de type {@link MotoRequestDto} contenant les nouvelles informations à appliquer à la moto.
     * @return un objet de type {@link MotoResponseDto} représentant la moto modifiée, après l'enregistrement des changements dans la base de données.
     * @throws VehiculeException si aucune moto n'est trouvée pour l'identifiant spécifié, ou si une erreur survient lors de la validation des données de la moto.
     */
    @Override
    public MotoResponseDto modifier(int id, MotoRequestDto motoRequestDto) throws VehiculeException {
        if (!motoDao.existsById(id)) throw new VehiculeException(JE_N_AI_PAS_TROUVER_L_ID);
        verifierMoto(motoRequestDto);
        Moto moto = motoMapper.toMoto(motoRequestDto);
        moto.setId(id);
        Moto motoEnreg = motoDao.save(moto);
        return motoMapper.toMotoResponseDto(motoEnreg);
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
        if (motoRequestDto == null) throw new VehiculeException("La voiture est null");
        if (motoRequestDto.marque() == null || motoRequestDto.marque().isBlank())
            throw new VehiculeException("La marque est obligatoire");
        if (motoRequestDto.modele() == null || motoRequestDto.modele().isBlank())
            throw new VehiculeException("Le modele est obligatoire");
        if (motoRequestDto.couleur() == null || motoRequestDto.couleur().isBlank())
            throw new VehiculeException("La couleur est obligatoire");
        if (motoRequestDto.permis() == null) throw new VehiculeException("Le permis est obligatoire");
        if (motoRequestDto.poid() == null || motoRequestDto.poid() == 0)
            throw new VehiculeException("Le poid est obligatoire et supperieur à 0");
        if (motoRequestDto.tarifJournalier() == null || motoRequestDto.tarifJournalier() == 0)
            throw new VehiculeException("Le tarif journalier est obligatoire et supperieur à 0");
        if (motoRequestDto.transmission() == null)
            throw new VehiculeException("Le type de transmission est obligatoire");
        if (motoRequestDto.nombreDeCylindre() == null || motoRequestDto.nombreDeCylindre() == 0)
            throw new VehiculeException("Le nombre de cylindre est obligatoire");
        if (motoRequestDto.cylindree() == null) throw new VehiculeException("Le nombre d'exception est obligatoire");
        if (motoRequestDto.puissanceKw() == null || motoRequestDto.puissanceKw() == 0)
            throw new VehiculeException("La puissance est obligatoire et supp à 0");
        if (motoRequestDto.kilometrage() == null || motoRequestDto.kilometrage() <= 0)
            throw new VehiculeException("Le kilometrage est obligatoire et supp à 0");

    }

}



