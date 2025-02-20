package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Override
    public AdminResponseDto ajouter(AdminRequestDto adminRequestDto) {
        return null;
    }

    @Override
    public List<AdminResponseDto> liste() {
        return List.of();
    }

    @Override
    public AdminResponseDto trouver(Long id) throws ClientException {
        return null;
    }
}
