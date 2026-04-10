package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Classe représentant un membre.
 *
 * @author Rodolphe BRUCKER
 * @version 1.0.0
 * @since 09/04/2026
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "membre")
public class Membre {
    /**
     * Identifiant du membre.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_membre")
    private Integer idMembre;

    /**
     * Nom du membre.
     */
    @Column(name = "nom_membre", length = 30, nullable = false)
    @NotBlank(message = "Un membre doit avoir un nom.")
    @Size(min = 3, max = 30,
          message = "Le nom du membre doit faire entre trois "
                    + "et cinquante caractères de long")
    private String nomMembre;

    /**
     * Prénom du membre.
     */
    @Column(name = "prenom_membre", length = 30, nullable = false)
    @NotBlank(message = "Un membre doit avoir un prénom.")
    @Size(min = 3, max = 30,
          message = "Le prénom du membre doit faire entre trois "
                    + "et trente caractères de long.")
    private String prenomMembre;

    /**
     * Date d'inscription du membre.
     */
    @Column(name = "date_inscription_membre", nullable = false)
    @NotNull(message = "Un membre doit avoir une date d'inscription.")
    @PastOrPresent(message = "Une date d'inscription ne peut pas être future.")
    private LocalDate dateInscriptionMembre;
}
