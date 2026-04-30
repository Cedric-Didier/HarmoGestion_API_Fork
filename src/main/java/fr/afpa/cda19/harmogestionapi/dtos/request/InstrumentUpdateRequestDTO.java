package fr.afpa.cda19.harmogestionapi.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de requête des instruments complets.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 27/04/2026
 */
@Getter
@AllArgsConstructor
public class InstrumentUpdateRequestDTO {

    /**
     * Identifiant de l'instrument.
     */
    @NotNull(message = "L'instrument doit avoir un identifiant")
    @Positive(message = "L'identifiant de l'instrument doit être positif")
    private Integer id;

    /**
     * Nom de l'instrument.
     */
    @NotBlank(message = "Un instrument doit avoir un nom")
    @Size(min = 3, max = 50,
          message = "Le nom de l'instrument doit faire entre trois "
                    + "et cinquante caractères de long")
    private String nom;
}
