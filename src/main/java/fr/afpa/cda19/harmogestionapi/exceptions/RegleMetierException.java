package fr.afpa.cda19.harmogestionapi.exceptions;
/**
 * Exception indiquant un non-respect d'une ou plusieurs règles métier.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 04/05/2026
 */
public class RegleMetierException extends RuntimeException {
    /**
     * Initialisation de l'exception.
     * @param message message pour l'utilisateur
     */
    public RegleMetierException(String message) {
        super(message);
    }
}
