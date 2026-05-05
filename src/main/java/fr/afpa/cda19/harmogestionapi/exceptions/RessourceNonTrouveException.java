package fr.afpa.cda19.harmogestionapi.exceptions;
/**
 * Exception indiquant une impossibilité de récupérer la ressource.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 04/05/2026
 */
public class RessourceNonTrouveException extends RuntimeException {
    /**
     * Initialisation de l'exception.
     * @param message message pour l'utilisateur
     */
    public RessourceNonTrouveException(String message) {
        super(message);
    }
}
