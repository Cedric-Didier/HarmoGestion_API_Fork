package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.services.InstrumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Contrôleur de gestion des requêtes concernant les instruments.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 08/04/2026
 */
@RestController
public class InstrumentController {

    /**
     * Service de liaison avec le repository des instruments.
     */
    private final InstrumentService service;

    /**
     * Constructeur d'initialisation du contrôleur.
     *
     * @param service service de liaison avec le repository des instruments
     */
    @Autowired
    public InstrumentController(final InstrumentService service) {
        this.service = service;
    }

    /**
     * Endpoint de récupèration de la liste des instruments.
     *
     * @return la liste récupérée
     */
    @GetMapping("/instruments")
    public Iterable<Instrument> getInstruments() {
        return service.getInstruments();
    }

    /**
     * Endpoint de création d'un nouvel instrument.
     *
     * @param instrument l'instrument à créer
     * @param result     le résultat de la validation de l'instrument
     * @return la response contenant l'instrument créé avec un code 201
     * ou un message d'erreur avec un code 400 ou 500
     */
    @PostMapping("/instrument")
    public ResponseEntity<Object> createInstrument(
            @RequestBody
            @Valid
            final Instrument instrument, final BindingResult result) {
        if (instrument.getIdInstrument() != null) {
            // L'instrument dans la requête ne doit pas avoir d'identifiant
            return new ResponseEntity<>((Instrument) null,
                                        HttpStatus.BAD_REQUEST);
        }
        // Récupèration des éventuelles erreurs de validation du libellé de
        // l'instrument
        FieldError error = result.getFieldError("libelleInstrument");
        if (error != null) {
            return new ResponseEntity<>(error.getDefaultMessage(),
                                        HttpStatus.BAD_REQUEST);
        }
        try {
            // Enregistrement de l'instrument
            Instrument savedInstrument = service.saveInstrument(instrument);
            return new ResponseEntity<>(savedInstrument, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException dive) {
            SQLException sqle = (SQLException) dive.getRootCause();
            if (sqle != null && sqle.getErrorCode() == 1062) {
                // Violation de l'unicité des libellés des instruments
                return new ResponseEntity<>("Cet instrument existe déjà.",
                                            HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Erreur inconnue.",
                                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Endpoint de récupèration d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument à récupérer
     * @return la réponse contenant l'instrument correspondant avec un code 200
     * ou message d'erreur avec un code 404
     */
    @GetMapping("/instrument/{id}")
    public Optional<Instrument> getInstrument(
            @PathVariable
            final int id) {
        return service.getInstrument(id);
    }

    /**
     * Endpoint de mise à jour d'un instrument.
     *
     * @param id         l'identifiant de l'instrument à modifier
     * @param instrument L'instrument modifié
     * @param result     Le résultat de la validation de l'instrument modifié
     * @return la réponse contenant l'instrument modifié avec un code 200
     * ou un message d'erreur avec un code 400 ou 500
     */
    @PutMapping("/instrument/{id}")
    public ResponseEntity<Object> updateInstrument(
            @PathVariable
            final int id,
            @RequestBody
            @Valid
            final Instrument instrument, final BindingResult result) {
        Optional<Instrument> optionalInstrument = service.getInstrument(id);
        if (optionalInstrument.isPresent()) {
            Instrument currentInstrument = optionalInstrument.get();
            // Vérification de la correspondance entre l'identifiant de l'URL
            // et l'identifiant de l'instrument donné en paramètre.
            if (instrument.getIdInstrument() != id) {
                return new ResponseEntity<>("L'instrument en paramètre n'a pas "
                                            + "le même identifiant",
                                            HttpStatus.BAD_REQUEST);
            }
            // Récupèration des éventuelles erreurs de validation du libellé de
            // l'instrument
            FieldError error = result.getFieldError("libelleInstrument");
            if (error != null) {
                return new ResponseEntity<>(error.getDefaultMessage(),
                                            HttpStatus.BAD_REQUEST);
            }
            String libelle = instrument.getLibelleInstrument();
            // Vérification de la présence d'une modification du nom de
            // l'instrument
            if (libelle != null && libelle.compareTo(
                    currentInstrument.getLibelleInstrument()) != 0) {
                currentInstrument.setLibelleInstrument(libelle);
                try {
                    service.saveInstrument(currentInstrument);
                } catch (DataIntegrityViolationException dive) {
                    SQLException sqle = (SQLException) dive.getRootCause();
                    if (sqle != null && sqle.getErrorCode() == 1062) {
                        // Violation de l'unicité des libellés des instruments
                        return new ResponseEntity<>(
                                "Cet instrument existe déjà.",
                                HttpStatus.BAD_REQUEST);
                    } else {
                        return new ResponseEntity<>(
                                "Erreur inconnue.",
                                HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
            return new ResponseEntity<>(currentInstrument, HttpStatus.OK);
        } else {
            // Aucun instrument n'a l'identifiant donné dans l'URL.
            return new ResponseEntity<>("La ressource n'est pas disponible.",
                                        HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint de suppression d'un instrument.
     *
     * @param id l'identifiant de l'instrument à supprimer
     */
    @DeleteMapping("/instrument/{id}")
    public void deleteInstrument(
            @PathVariable
            final int id) {
        service.deleteInstrument(id);
    }
}
