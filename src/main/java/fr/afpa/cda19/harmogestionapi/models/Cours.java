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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours")
    private Integer idCours;

    @Column(name = "date_cours", nullable = false)
    @NotNull(message = "Le cours doit avoir une date")
    private LocalDateTime dateCours;

    @Column(name = "duree_cours", nullable = false)
    @Min(value = 30, message = "Le cours doit durer au moins 10 minutes")
    @Max(value = 120, message = "Le cours doit durer au maximum 120 minutes")
    private byte dureeCours;

    @ManyToOne
    @JoinColumn(name = "id_membre_enseignant", nullable = false)
    @Valid
    private Membre enseignant;

    @ManyToOne
    @JoinColumn(name = "id_instrument", nullable = false)
    @Valid
    private Instrument instrument;

    @ManyToMany
    @JoinTable(
            name = "Participer_Cours",
            joinColumns = @JoinColumn(name = "id_cours"),
            inverseJoinColumns = @JoinColumn(name = "id_membre_apprenant")
    )
    private ArrayList<Membre> participants;
}
