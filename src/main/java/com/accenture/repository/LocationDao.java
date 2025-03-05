package com.accenture.repository;

import com.accenture.repository.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LocationDao extends JpaRepository<Location, Integer> {

    @Query("SELECT l FROM Location l WHERE " +
            "(l.dateDeDebut BETWEEN :startDate AND :endDate) OR " +
            "(l.dateDeFin BETWEEN :startDate AND :endDate) OR " +
            "(l.dateDeDebut <= :startDate AND l.dateDeFin >= :endDate)")
    List<Location> findLocationsByPeriod(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate);


}
