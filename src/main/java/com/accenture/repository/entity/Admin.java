package com.accenture.repository.entity;

import com.accenture.model.Fonction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("ADMIN")
@EqualsAndHashCode(callSuper = true)

public class Admin extends UtilisateurConnecte {

    private Fonction fonction;
}
