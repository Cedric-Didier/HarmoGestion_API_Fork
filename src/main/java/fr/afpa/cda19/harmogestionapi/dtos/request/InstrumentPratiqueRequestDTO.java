package fr.afpa.cda19.harmogestionapi.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Classe DTO de la pratique d'un instrument.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 27/04/2026
 */
@Getter
@AllArgsConstructor
public class InstrumentPratiqueRequestDTO {

    /**
     * Identifiant de l'instrument pratiqué.
     */
    @NotNull(message = "L'instrument pratiqué doit avoir un identifiant")
    @Positive(message = "L'identifiant de l'instrument pratiqué doit être positif.")
    private Integer id;

    /**
     * Booléen traduisant le fait que la pratique de l'instrument est en cours
     * d'apprentissage ou non.
     */
    @NotNull(message = "Il est obligatoire de préciser si l'instrument pratiqué est en cours "
                       + "d'apprentissage ou non")
    private Boolean apprentissageEnCours;
}
