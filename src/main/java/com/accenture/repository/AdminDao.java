package com.accenture.repository;

import com.accenture.repository.entity.Admin;
import com.accenture.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin, String> {

    Optional<Admin> findByEmailAndPassword(String email, String password);


}
