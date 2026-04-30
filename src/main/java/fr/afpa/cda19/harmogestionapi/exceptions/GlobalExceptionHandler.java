package fr.afpa.cda19.harmogestionapi.exceptions;

import fr.afpa.cda19.harmogestionapi.dtos.response.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegleMetierException.class)
    public ResponseEntity<ApiError> handleErreurRegleMetier(final RegleMetierException rme) {
        return buildResponse(HttpStatus.UNPROCESSABLE_CONTENT, rme.getMessage(), null);
    }

    @ExceptionHandler(RessourceNonTrouveException.class)
    public ResponseEntity<ApiError> handleRessourceNonTrouvee(
            final RessourceNonTrouveException rnte) {
        return buildResponse(HttpStatus.NOT_FOUND, rnte.getMessage(), null);
    }

    @ExceptionHandler(RessourceDupliqueeException.class)
    public ResponseEntity<ApiError> handleRessourceDupliquee(
            final RessourceDupliqueeException rde) {
        return buildResponse(HttpStatus.CONFLICT, rde.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleErreursValidation(
            final MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return buildResponse(HttpStatus.BAD_REQUEST, "Échec de la validation", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleErreurGenerale(Exception e){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur est survenue.", null);
    }

    private ResponseEntity<ApiError> buildResponse(final HttpStatus status, final String message,
                                                   final Map<String, String> details) {
        ApiError error = new ApiError(LocalDateTime.now(), status.value(), message, details);
        return new ResponseEntity<>(error, status);
    }
}
