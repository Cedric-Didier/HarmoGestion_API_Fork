package fr.afpa.cda19.harmogestionapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant la pratique d'un instrument par un membre.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 22/04/2026
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pratiquer")
public class Pratiquer {

    @EmbeddedId
    private PratiquerPK id;

    /**
     * Identifiant de l'instrument.
     */

    @ManyToOne
    @JoinColumn(name = "id_instrument")
    @MapsId("idInstrument")
    @NotNull(message = "l'identifiant de l'instrument est obligatoire.")
    private Instrument instrument;

    /**
     * Identifiant du membre.
     */
    @ManyToOne
    @MapsId("idMembre")
    @JoinColumn(name = "id_membre")
    @JsonBackReference
    @NotNull(message = "L'identifiant du membre est obligatoire.")
    private Membre membre;

    /**
     * Booléen indiquant si la pratique de l'instrument par le membre est en
     * cours d'apprentissage.
     */
    @Column(name = "apprentissage_en_cours")
    private Boolean apprentissageEnCours;
}
