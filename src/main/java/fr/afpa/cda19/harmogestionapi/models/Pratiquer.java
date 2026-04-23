package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@IdClass(PratiquerPK.class)
public class Pratiquer {

    /**
     * Identifiant de l'instrument.
     */
    @Id
    @Column(name = "id_instrument")
    @NotNull(message = "l'identifiant de l'instrument est obligatoire.")
    private Integer idInstrument;

    /**
     * Identifiant du membre.
     */
    @Id
    @Column(name = "id_membre")
    @NotNull(message = "L'identifiant du membre est obligatoire.")
    private Integer idMembre;

    /**
     * Booléen indiquant si la pratique de l'instrument par le membre est en
     * cours d'apprentissage.
     */
    @Column(name = "apprentissage_en_cours")
    private Boolean apprentissageEnCours;
}
