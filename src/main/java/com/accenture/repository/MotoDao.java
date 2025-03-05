package com.accenture.repository;

import com.accenture.repository.entity.vehicules.Moto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoDao extends JpaRepository<Moto, Integer> {
}
