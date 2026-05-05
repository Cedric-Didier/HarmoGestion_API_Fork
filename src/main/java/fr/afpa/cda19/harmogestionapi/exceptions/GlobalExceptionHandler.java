package fr.afpa.cda19.harmogestionapi.exceptions;

import fr.afpa.cda19.harmogestionapi.dtos.response.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegleMetierException.class)
    protected ResponseEntity<Object> handleErreurRegleMetier(final RegleMetierException rme,
                                                             final HttpServletRequest request) {
        return buildResponse(
                request.getRequestURI(),
                HttpStatus.UNPROCESSABLE_CONTENT,
                rme.getMessage(),
                new HashMap<>()
        );
    }

    @ExceptionHandler(RessourceNonTrouveException.class)
    protected ResponseEntity<Object> handleRessourceNonTrouvee(
            final RessourceNonTrouveException rnte, final HttpServletRequest request) {
        return buildResponse(
                request.getRequestURI(),
                HttpStatus.NOT_FOUND,
                rnte.getMessage(),
                new HashMap<>()
        );
    }

    @ExceptionHandler(RessourceDupliqueeException.class)
    protected ResponseEntity<Object> handleRessourceDupliquee(
            final RessourceDupliqueeException rde, final HttpServletRequest request) {
        return buildResponse(
                request.getRequestURI(),
                HttpStatus.CONFLICT,
                rde.getMessage(),
                new HashMap<>()
        );
    }

    @ExceptionHandler(IdNonCorrespondantException.class)
    protected ResponseEntity<Object> handleIdNonCorrespondant(
            final IdNonCorrespondantException ince,
            final HttpServletRequest request) {
        return buildResponse(
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST,
                ince.getMessage(),
                new HashMap<>()
        );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleErreurGenerale(final Exception e,
                                                          final HttpServletRequest request) {
        log.error("Erreur serveur non gérée : ", e);
        return buildResponse(
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Une erreur est survenue.",
                new HashMap<>()
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatusCode status,
            final @NonNull WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return buildResponse(
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                HttpStatus.BAD_REQUEST,
                "Échec de la validation",
                errors
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final HttpRequestMethodNotSupportedException ex,
            final @NonNull HttpHeaders headers,
            final HttpStatusCode status,
            final @NonNull WebRequest request) {

        // On peut récupérer les méthodes autorisées pour cette route
        String supportedMethods = Optional.ofNullable(ex.getSupportedHttpMethods())
                .map(Object::toString)
                .orElse("None");

        Map<String, String> details = new HashMap<>();
        details.put("erreur", "Méthode non supportée");
        details.put("méthodes supportées", supportedMethods);

        return buildResponse(
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                HttpStatus.valueOf(status.value()),
                "La méthode " + ex.getMethod() + " n'est pas supportée pour cette requête.",
                details
        );
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            final NoResourceFoundException ex,
            final @NonNull HttpHeaders headers,
            final HttpStatusCode status,
            final @NonNull WebRequest request) {

        Map<String, String> details = new HashMap<>();
        details.put("chemin_demande", ex.getResourcePath());

        return buildResponse(
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                HttpStatus.valueOf(status.value()),
                "La ressource demandée n'existe pas",
                details
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            final HttpMediaTypeNotSupportedException ex,
            final @NonNull HttpHeaders headers,
            final HttpStatusCode status,
            final @NonNull WebRequest request) {

        String provided = Optional.ofNullable(ex.getContentType())
                .map(Object::toString)
                .orElse("Inconnu");
        String supported = ex.getSupportedMediaTypes().toString();

        Map<String, String> details = new HashMap<>();
        details.put("type_fourni", provided);
        details.put("types_supportes", supported);

        return buildResponse(
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                HttpStatus.valueOf(status.value()),
                "Type de média non supporté",
                details
        );
    }

    private ResponseEntity<Object> buildResponse(final String path, final HttpStatus status,
                                                 final String message,
                                                 final Map<String, String> details) {
        ApiError error = new ApiError(LocalDateTime.now(), path, status.value(), message, details);
        return new ResponseEntity<>(error, status);
    }
}
