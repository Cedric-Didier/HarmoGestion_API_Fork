package fr.afpa.cda19.harmogestionapi.dtos.response;

/**
 * DTO de réponse des instruments.
 *
 * @param id  Identifiant de l'instrument.
 * @param nom Nom de l'instrument.
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 28/04/2026
 */
public record InstrumentResponseDTO(
        Integer id,
        String nom) {

}
