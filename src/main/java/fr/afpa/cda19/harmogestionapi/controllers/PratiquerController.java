package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Pratiquer;
import fr.afpa.cda19.harmogestionapi.models.PratiquerPK;
import fr.afpa.cda19.harmogestionapi.services.PratiquerService;
import fr.afpa.cda19.harmogestionapi.utilities.PratiquerResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class PratiquerController {

    private static final Pattern ID_REGEX = Pattern.compile("(id_\\w+)");

    private final PratiquerService service;

    @Autowired
    public PratiquerController(final PratiquerService service) {
        this.service = service;
    }

    @PostMapping("/pratique")
    public ResponseEntity<PratiquerResponse> createPratiqueInstrument(
            @RequestBody
            @Valid
            final Pratiquer pratiquer, final BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).collect(
                            Collectors.toList());
            return new ResponseEntity<>(new PratiquerResponse(null, errors),
                                        HttpStatus.BAD_REQUEST);
        }
        if (service.pratiqueExistById(new PratiquerPK(
                pratiquer.getInstrument().getIdInstrument(),
                pratiquer.getMembre().getIdMembre()))) {
            List<String> errors =
                    List.of("Ce membre pratique déjà cet instrument");
            return new ResponseEntity<>(new PratiquerResponse(null, errors),
                                        HttpStatus.BAD_REQUEST);
        }
        try {
            Pratiquer savedPratiquer =
                    service.savePratiqueInstrument(pratiquer);
            return new ResponseEntity<>(
                    new PratiquerResponse(savedPratiquer, null),
                    HttpStatus.OK);
        } catch (DataIntegrityViolationException dive) {
            SQLException sqle = (SQLException) dive.getRootCause();
            List<String> errors;
            if (sqle != null && sqle.getErrorCode() == 1452) {
                Matcher matcher = ID_REGEX.matcher(sqle.getMessage());
                String test;
                if (matcher.find()) {
                    test = matcher.group(1);
                } else {
                    test = "pas de match";
                }
                switch (test) {
                    case "id_membre" -> errors = List.of("Le membre n'existe pas");
                    case "id_instrument" -> errors = List.of("L'instrument n'existe pas");
                    default -> errors = List.of("erreur inconnue");
                }
                return new ResponseEntity<>(new PratiquerResponse(null, errors),
                                            HttpStatus.BAD_REQUEST);
            } else {
                errors = List.of("Erreur inconnue");
                return new ResponseEntity<>(new PratiquerResponse(null, errors),
                                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PutMapping("/pratique")
    public ResponseEntity<PratiquerResponse> updatePratique(
            @RequestBody
            @Valid
            final Pratiquer pratiquer, final BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).collect(
                            Collectors.toList());
            return new ResponseEntity<>(new PratiquerResponse(null, errors),
                                        HttpStatus.BAD_REQUEST);
        }
        if (!service.pratiqueExistById(new PratiquerPK(
                pratiquer.getInstrument().getIdInstrument(),
                pratiquer.getMembre().getIdMembre()))) {
            List<String> errors =
                    List.of("Ce membre ne pratique pas cet instrument");
            return new ResponseEntity<>(new PratiquerResponse(null, errors),
                                        HttpStatus.BAD_REQUEST);
        }
        try {
            Pratiquer savedPratiquer =
                    service.savePratiqueInstrument(pratiquer);
            return new ResponseEntity<>(
                    new PratiquerResponse(savedPratiquer, null),
                    HttpStatus.OK);
        } catch (DataIntegrityViolationException dive) {
            SQLException sqle = (SQLException) dive.getRootCause();
            List<String> errors;
            if (sqle != null && sqle.getErrorCode() == 1452) {
                Matcher matcher = ID_REGEX.matcher(sqle.getMessage());
                String test;
                if (matcher.find()) {
                    test = matcher.group(1);
                } else {
                    test = "pas de match";
                }
                switch (test) {
                    case "id_membre" -> errors = List.of("Le membre n'existe pas");
                    case "id_instrument" -> errors = List.of("L'instrument n'existe pas");
                    default -> errors = List.of("erreur inconnue");
                }
                return new ResponseEntity<>(new PratiquerResponse(null, errors),
                                            HttpStatus.BAD_REQUEST);
            } else {
                errors = List.of("Erreur inconnue");
                return new ResponseEntity<>(new PratiquerResponse(null, errors),
                                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping("/pratique")
    public void deletePratique(
            @RequestBody
            final PratiquerPK pratiquerPK) {
        service.deletePratiqueInstrument(pratiquerPK);
    }
}
