package fr.afpa.cda19.harmogestionapi.exceptions;
/**
 * Exception indiquant une duplication d'une ressource.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 04/05/2026
 */
public class RessourceDupliqueeException extends RuntimeException {
    /**
     * Initialisation de l'exception.
     * @param message message pour l'utilisateur
     */
    public RessourceDupliqueeException(String message) {
        super(message);
    }
}
