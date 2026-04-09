package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
@Table(name = "Cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours")
    private Integer idCours;

    @Column(name = "date_cours")
    @NotNull(message = "Le cours doit avoir une date")
    private LocalDateTime dateCours;

    @Column(name = "duree_cours")
    @Min(value = 30, message = "Le cours doit durer au moins 10 minutes")
    @Max(value = 120, message = "Le cours doit durer au maximum 120 minutes")
    private byte dureeCours;

//    @OneToMany
//    @JoinColumn(name = "id_membre_enseignant")
//    @Valid
//    private Membre enseignant;
//
//    @OneToMany
//    @JoinColumn(name = "id_instrument")
//    @Valid
//    private Instrument instrument;
//
//    @ManyToMany
//    @JoinTable(
//            name = "Participer_Cours",
//            joinColumns = @JoinColumn(name = "id_membre_apprenant"),
//            inverseJoinColumns = @JoinColumn(name = "id_cours")
//    )
//    private ArrayList<Membre> participants;
}
