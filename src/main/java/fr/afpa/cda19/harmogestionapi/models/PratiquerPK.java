package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class PratiquerPK {

    /**
     * Identifiant de l'instrument.
     */
    @NotNull(message = "l'identifiant de l'instrument est obligatoire.")
    private Integer idInstrument;

    /**
     * Identifiant du membre.
     */
    @NotNull(message = "L'identifiant du membre est obligatoire.")
    private Integer idMembre;
}
