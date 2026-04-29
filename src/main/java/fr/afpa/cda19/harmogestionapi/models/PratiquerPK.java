package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * Classe représentant l'identifiant de la pratique d'un instrument par un
 * membre.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 22/04/2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PratiquerPK implements Serializable {

    /**
     * Identifiant de l'instrument.
     */
    private Integer idInstrument;

    /**
     * Identifiant du membre.
     */
    private Integer idMembre;
}
