package com.accenture.repository;

import com.accenture.repository.entity.Admin;
import com.accenture.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientDao extends JpaRepository<Client, String> {

    Optional<Client> findByEmailAndPassword(String email, String password);
Optional<Client> deleteByEmailAndPassword(String email, String password);

}
