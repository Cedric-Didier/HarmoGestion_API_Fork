package fr.afpa.cda19.harmogestionapi.dtos.response;

import java.time.LocalDate;
import java.util.Set;

/**
 * DTO de réponse pour les membres.
 *
 * @param id              Identifiant du membre
 * @param nom             Nom du membre
 * @param prenom          Prénom du membre
 * @param dateInscription Date d'inscription du membre
 * @param instruments     Liste des instruments pratiqués
 */
public record MembreResponseDTO(
        Integer id,
        String nom,
        String prenom,
        LocalDate dateInscription,
        Set<InstrumentPratiqueResponseDTO> instruments) {

}
