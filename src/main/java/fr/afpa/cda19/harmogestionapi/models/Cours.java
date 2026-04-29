package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Model des cours.
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 08/04/2026
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cours")
public class Cours {

    /**
     * Identifiant.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours")
    private Integer idCours;

    /**
     * Date du cours.
     */
    @Column(name = "date_cours", nullable = false)
    @NotNull(message = "Le cours doit avoir une date")
    private LocalDateTime dateCours;

    /**
     * Durée du cours (en min).
     */
    @Column(name = "duree_cours", nullable = false)
    @Min(value = 30, message = "Le cours doit durer au moins 30 minutes")
    @Max(value = 120, message = "Le cours doit durer au maximum 120 minutes")
    private byte dureeCours;

    /**
     * Enseignant.
     */
    @ManyToOne
    @JoinColumn(name = "id_membre_enseignant", nullable = false)
    @NotNull
    private Membre enseignant;

    /**
     * Instrument enseigné.
     */
    @ManyToOne
    @JoinColumn(name = "id_instrument", nullable = false)
    @NotNull
    private Instrument instrument;

    /**
     * Liste des participants.
     */
    @ManyToMany
    @JoinTable(
            name = "participer_cours",
            joinColumns = @JoinColumn(name = "id_cours"),
            inverseJoinColumns = @JoinColumn(name = "id_membre_apprenant")
    )
    @NotNull(message = "Le cours doit avoir des participants")
    @Size(min = 1, max = 15, message = "Le nombre de participants doit être "
                                       + "entre 1 et 15")
    private List<Membre> participants;
}
