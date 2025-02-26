package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.Admin;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
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
     * @param adminRequestDto
     * @return
     */


    @Override
    public AdminResponseDto ajouter(AdminRequestDto adminRequestDto) {
        verifierAdmin(adminRequestDto);
        Admin admin = adminMapper.toAdmin(adminRequestDto);
        Admin adminEnreg = adminDao.save(admin);
        return adminMapper.toAdminResponseDto(adminEnreg);
    }


    @Override
    public List<AdminResponseDto> trouverToutes() {
        return adminDao
                .findAll()
                .stream()
                .map(adminMapper::toAdminResponseDto)
                .toList();


    }



    @Override
    public AdminResponseDto trouver(String email, String password) throws AdminException {
        Optional<Admin> optAdmin = adminDao.findByEmailAndPassword(email, password);
        if (optAdmin.isEmpty())
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_EMAIL);
        Admin admin = optAdmin.get();
        return adminMapper.toAdminResponseDto((admin));
    }

    @Override
    public AdminResponseDto modifier(String email, AdminRequestDto adminRequestDto) throws AdminException {
        if (!adminDao.existsById(email))
            throw new EntityNotFoundException(JE_N_AI_PAS_TROUVER_L_EMAIL);
        verifierAdmin(adminRequestDto);
        Admin admin = adminMapper.toAdmin(adminRequestDto);
        admin.setEmail(email);
        Admin adminEnreg = adminDao.save(admin);

        return adminMapper.toAdminResponseDto(adminEnreg);
    }

    @Override
    public void supprimer(String email, String password) throws AdminException {
        Admin admin = adminDao.findByEmailAndPassword(email, password).orElseThrow(() -> new EntityNotFoundException("Admin non trouvé"));
        adminDao.delete(admin);
    }

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


}
