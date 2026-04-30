package fr.afpa.cda19.harmogestionapi.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO des requêtes de l'instrument enseigné dans un cours.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 28/04/2026
 */
@Getter
@AllArgsConstructor
public class InstrumentIdRequestDTO {

    /**
     * Identifiant de l'instrument.
     */
    @NotNull(message = "L'instrument enseigné doit avoir un identifiant")
    @Positive(message = "L'identifiant de l'instrument enseigné doit être positif")
    private Integer id;
}
