package fr.afpa.cda19.harmogestionapi.dtos.response;

/**
 * DTO de réponse des instruments pratiqués.
 *
 * @param id                   Identifiant de l'instrument
 * @param nom                  Nom de l'instrument
 * @param apprentissageEnCours Booléen indiquant si l'instrument est en cours d'apprentissage ou non
 */
public record InstrumentPratiqueResponseDTO(Integer id, String nom, Boolean apprentissageEnCours) {

}
