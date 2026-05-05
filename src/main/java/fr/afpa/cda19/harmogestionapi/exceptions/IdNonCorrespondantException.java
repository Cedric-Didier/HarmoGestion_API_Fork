package fr.afpa.cda19.harmogestionapi.exceptions;

/**
 * Exception indiquant une non-correspondance entre l'id de l'URL et l'id du DTO.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 04/05/2026
 */
public class IdNonCorrespondantException extends RuntimeException {

    /**
     * Initialisation de l'exception.
     * @param message message pour l'utilisateur
     */
    public IdNonCorrespondantException(String message) {
        super(message);
    }
}
