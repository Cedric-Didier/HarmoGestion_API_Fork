package fr.afpa.cda19.harmogestionapi.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO de requête de mise à jour des cours.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 29/08/2026
 */
@Getter
@AllArgsConstructor
public class CoursUpdateRequestDTO {

    /**
     * Identifiant du cours.
     */
    @NotNull(message = "Le cours doit avoir un identifiant")
    @Positive(message = "L'identifiant du cours doit être positif")
    private Integer id;

    /**
     * Date du cours.
     */
    @NotNull(message = "Le cours doit avoir une date.")
    private LocalDateTime dateCours;

    /**
     * Durée du cours (en min).
     */
    @NotNull(message = "Le cours doit avoir une durée.")
    @Min(value = 30, message = "Le cours doit durer au moins 30 minutes.")
    @Max(value = 120, message = "Le cours doit durer au maximum 120 minutes.")
    private Byte dureeCours;

    /**
     * Enseignant.
     */
    @NotNull(message = "Le cours doit être dispensé par un membre.")
    @Valid
    private MembreIdRequestDTO enseignant;

    /**
     * Instrument enseigné.
     */
    @NotNull(message = "Le cours doit concerner un instrument.")
    @Valid
    private InstrumentIdRequestDTO instrument;

    /**
     * Liste des participants.
     */
    @Size(max = 15,
          message = "Le nombre de participants doit être de 15 au maximum.")
    private Set<@Valid MembreIdRequestDTO> participants;
}
