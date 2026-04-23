package fr.afpa.cda19.harmogestionapi.utilities;

import fr.afpa.cda19.harmogestionapi.models.Pratiquer;

import java.util.List;

/**
 * Enregistrement du résultat du contrôle des endpoints concernant l'entité
 * Pratiquer.
 *
 * @param pratiquer Entité Pratiquer demandée ou sauvegardée
 * @param errors    Liste des erreurs de contrôle
 */
public record PratiquerResponse(Pratiquer pratiquer, List<String> errors) {

}
