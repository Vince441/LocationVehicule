package com.accenture.advice;

import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.exception.locations.LocationException;
import com.accenture.exception.vehicules.VehiculeException;
import com.accenture.model.ErreurReponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErreurReponse> gestionClientException(ClientException ex) {
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur liée au client", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErreurReponse> gestionEntityNotFoundException(EntityNotFoundException ex) {
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "L'entité n'a pas été trouvé", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);

    }


    @ExceptionHandler(AdminException.class)
    public ResponseEntity<ErreurReponse> gestionAdminException(AdminException ex) {
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur liée à l'admin", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }


    @ExceptionHandler(VehiculeException.class)
    public ResponseEntity<ErreurReponse> gestionVehiculeException(VehiculeException ex) {
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur liée au véhicule", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(LocationException.class)
    public ResponseEntity<ErreurReponse> gestionLocationException(LocationException ex) {
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur liée à la location", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

}
