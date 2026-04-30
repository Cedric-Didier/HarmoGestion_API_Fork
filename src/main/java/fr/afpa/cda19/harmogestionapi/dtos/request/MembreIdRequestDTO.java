package fr.afpa.cda19.harmogestionapi.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO des requêtes des membres participants et enseignant dans un cours.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 28/04/2026
 */
@Getter
@AllArgsConstructor
public class MembreIdRequestDTO {

    /**
     * Identifiant du membre.
     */
    @NotNull(message = "Le membre doit avoir un identifiant")
    @Positive(message = "L'identifiant du membre doit être positif")
    private Integer id;
}
