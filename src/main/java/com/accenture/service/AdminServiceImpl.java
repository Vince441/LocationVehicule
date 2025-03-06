package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.Admin;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminServiceImpl implements AdminService {

    public static final String JE_N_AI_PAS_TROUVER_L_EMAIL = "Je n'ai pas trouvé l'email";
    private final AdminDao adminDao;
    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminDao adminDao, AdminMapper adminMapper) {
        this.adminDao = adminDao;
        this.adminMapper = adminMapper;
    }


    /**
     * Ajoute un nouvel administrateur dans la base de données.
     * Cette méthode valide les informations fournies dans le DTO {@link AdminRequestDto},
     * puis crée un nouvel administrateur en les enregistrant dans la base de données.
     *
     * @param adminRequestDto un objet {@link AdminRequestDto} contenant les informations de l'administrateur à ajouter.
     * @return un objet {@link AdminResponseDto} représentant l'administrateur ajouté, après l'enregistrement dans la base de données.
     * @throws VehiculeException si les informations de l'administrateur ne sont pas valides, déclenchant une exception dans la méthode {@link #verifierAdmin(AdminRequestDto)}.
     */
    @Override
    public AdminResponseDto ajouter(AdminRequestDto adminRequestDto) {
        verifierAdmin(adminRequestDto);
        Admin admin = adminMapper.toAdmin(adminRequestDto);
        Admin adminEnreg = adminDao.save(admin);
        return adminMapper.toAdminResponseDto(adminEnreg);
    }

    /**
     * Récupère la liste de tous les administrateurs enregistrés dans la base de données.
     * Chaque administrateur est retourné sous forme de DTO {@link AdminResponseDto}.
     *
     * @return une liste d'objets {@link AdminResponseDto} représentant tous les administrateurs présents dans la base de données.
     */
    @Override
    public List<AdminResponseDto> trouverToutes() {
        return adminDao
                .findAll()
                .stream()
                .map(adminMapper::toAdminResponseDto)
                .toList();
    }

    /**
     * Recherche un administrateur dans la base de données en fonction de son adresse email et de son mot de passe.
     * Si un administrateur correspondant est trouvé, un DTO {@link AdminResponseDto} est retourné.
     * Si l'administrateur n'est pas trouvé, une exception est levée.
     *
     * @param email    l'adresse email de l'administrateur à rechercher.
     * @param password le mot de passe de l'administrateur à rechercher.
     * @return un objet {@link AdminResponseDto} représentant l'administrateur trouvé.
     * @throws AdminException          si l'administrateur n'est pas trouvé pour l'email et le mot de passe fournis.
     * @throws EntityNotFoundException si aucun administrateur n'est trouvé avec les informations données.
     */
    @Override
    public AdminResponseDto trouver(String email, String password) throws AdminException {
        Optional<Admin> optAdmin = adminDao.findByEmailAndPassword(email, password);
        if (optAdmin.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_EMAIL);
        Admin admin = optAdmin.get();
        return adminMapper.toAdminResponseDto((admin));
    }




    @Override
    public AdminResponseDto modifierPartiellement(String email, String password, AdminRequestDto adminRequestDto) throws AdminException {
        Optional<Admin> optAdmin = adminDao.findByEmailAndPassword(email, password);
        if (optAdmin.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_EMAIL);

        Admin adminExistant = optAdmin.get();
        Admin adminEnreg = adminMapper.toAdmin(adminRequestDto);

        remplacer(adminExistant, adminEnreg);

        Admin modifAdmin = adminDao.save(adminExistant);
        return adminMapper.toAdminResponseDto(modifAdmin);

    }



    /**
     * Supprime un administrateur de la base de données en fonction de son email et de son mot de passe.
     * Si un administrateur correspondant est trouvé, il est supprimé de la base de données.
     * Si l'administrateur n'est pas trouvé, une exception est levée.
     *
     * @param email    l'adresse email de l'administrateur à supprimer.
     * @param password le mot de passe de l'administrateur à supprimer.
     * @throws AdminException          si une erreur se produit lors de la suppression de l'administrateur.
     * @throws EntityNotFoundException si aucun administrateur n'est trouvé avec les informations données (email et mot de passe).
     */
    @Override
    public void supprimer(String email, String password) throws AdminException {
        Admin admin = adminDao.findByEmailAndPassword(email, password).orElseThrow(() -> new EntityNotFoundException("Admin non trouvé"));
        adminDao.delete(admin);
    }

    /**
     * Vérifie que les informations contenues dans le DTO {@link AdminRequestDto} sont valides.
     * Cette méthode vérifie si tous les champs obligatoires sont présents et non vides.
     *
     * @param adminRequestDto l'objet {@link AdminRequestDto} contenant les informations de l'administrateur à vérifier.
     * @throws AdminException si une des informations obligatoires est manquante ou invalide.
     */
    private static void verifierAdmin(AdminRequestDto adminRequestDto) throws AdminException {
        if (adminRequestDto == null)
            throw new ClientException("Le client est null");
        if (adminRequestDto.nom() == null || adminRequestDto.nom().isBlank())
            throw new ClientException("le nom est obligatoire");
        if (adminRequestDto.prenom() == null || adminRequestDto.prenom().isBlank())
            throw new ClientException("le prenom est obligatoire");
        if (adminRequestDto.email() == null || adminRequestDto.email().isBlank())
            throw new ClientException("L'email est obligatoire");
        if (adminRequestDto.password() == null || adminRequestDto.password().isBlank())
            throw new ClientException("le password est obligatoire");
    }



    private static void remplacer(Admin adminExistant, Admin adminEnreg) {
        if (adminEnreg.getNom() != null)
            adminExistant.setNom(adminEnreg.getNom());
        if (adminEnreg.getPrenom() != null)
            adminExistant.setPrenom(adminEnreg.getPrenom());
        if (adminEnreg.getEmail() != null)
            adminExistant.setEmail(adminEnreg.getEmail());
        if (adminEnreg.getPassword() != null)
            adminExistant.setPassword(adminEnreg.getPassword());

    }


}
