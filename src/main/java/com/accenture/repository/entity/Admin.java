package com.accenture.repository.entity;

import com.accenture.model.Fonction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("ADMIN")

public class Admin extends UtilisateurConnecte {

    private Fonction fonction;
}
