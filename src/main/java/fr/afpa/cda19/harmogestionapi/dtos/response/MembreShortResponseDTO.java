package fr.afpa.cda19.harmogestionapi.dtos.response;

import java.time.LocalDate;

/**
 * DTO de réponse des membres participants et enseignant dans un cours.
 *
 * @param id              Identifiant du membre
 * @param nom             Nom du membre
 * @param prenom          Prénom du membre
 * @param dateInscription Date d'inscription du membre
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 28/04/2026
 */
public record MembreShortResponseDTO(
        Integer id,
        String nom,
        String prenom,
        LocalDate dateInscription) {

}
