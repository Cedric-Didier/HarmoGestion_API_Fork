package fr.afpa.cda19.harmogestionapi.dtos.response;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO de réponse des cours.
 *
 * @param id           Identifiant du cours
 * @param date    Date du cours
 * @param duree   Durée du cours (en min)
 * @param enseignant   Membre enseignant
 * @param instrument   Instrument enseigné
 * @param participants Liste des participants
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 28/04/2026
 */
public record CoursResponseDTO(
        Integer id,
        LocalDateTime date,
        Byte duree,
        MembreShortResponseDTO enseignant,
        InstrumentResponseDTO instrument,
        Set<MembreShortResponseDTO> participants) {

}
