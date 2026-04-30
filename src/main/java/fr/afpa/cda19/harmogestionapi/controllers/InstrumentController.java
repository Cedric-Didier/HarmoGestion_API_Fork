package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.dtos.request.InstrumentCreateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.response.InstrumentResponseDTO;
import fr.afpa.cda19.harmogestionapi.exceptions.RessourceDupliqueeException;
import fr.afpa.cda19.harmogestionapi.exceptions.RessourceNonTrouveException;
import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.services.InstrumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur de gestion des requêtes concernant les instruments.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 08/04/2026
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Instruments", description = "Gestion des instruments")
public class InstrumentController {

    /**
     * Service de liaison avec le repository des instruments.
     */
    private final InstrumentService service;

    /**
     * Endpoint de récupèration de la liste des instruments.
     *
     * @return la liste récupérée
     */
    @GetMapping("/instruments")
    @Operation(
            summary = "Lister les instruments",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            [
                                              {
                                               "id":1,
                                               "nom":"Harpe"
                                              }
                                            ]
                                            """
                            )
                    )
            )
    )
    public List<InstrumentResponseDTO> getInstruments() {
        return service.getInstruments();
    }

    /**
     * Endpoint de création d'un nouvel instrument.
     *
     * @param instrument l'instrument à créer
     * @return la response contenant l'instrument créé avec un code 201
     */
    @PostMapping("/instrument")
    @Operation(
            summary = "Création d'un instrument",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "l'instrument à enregistrer",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                             "libelleInstrument":"Harpe"
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Instrument créé",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "idInstrument":15,
                                                      "libelleInstrument":"Harpe"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Échec de la validation du DTO",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "timestamp":"1970-01-01T00:00:00.0000000",
                                                      "statut":400,
                                                      "message":"Échec de la validation",
                                                      "details":[
                                                                  {
                                                                    "nom":"string"
                                                                  }
                                                                ]
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Duplication du nom d'un instrument",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "timestamp":"1970-01-01T00:00:00.0000000",
                                                      "statut":409,
                                                      "message":"Cet instrument existe déjà",
                                                      "details":null
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<InstrumentResponseDTO> createInstrument(
            @org.springframework.web.bind.annotation.RequestBody
            @Valid
            final InstrumentCreateRequestDTO instrument) throws RessourceDupliqueeException {
        // Enregistrement de l'instrument
        InstrumentResponseDTO savedInstrument = service.createInstrument(instrument);
        return new ResponseEntity<>(savedInstrument, HttpStatus.CREATED);
    }

    /**
     * Endpoint de récupèration d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument à récupérer
     * @return la réponse contenant l'instrument correspondant avec un code 200
     * ou message d'erreur avec un code 404
     */
    @GetMapping("/instrument/{id}")
    @Operation(
            summary = "Récupération d'un instrument par son id",
            parameters = @Parameter(
                    name = "id",
                    description = "identifiant de l'instrument",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Instrument trouvé",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "id":1,
                                                      "nom":"Harpe"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Impossible de trouver l'instrument",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "timestamp":"1970-01-01T00:00:00.0000000",
                                                      "statut":404,
                                                      "message":"Impossible de trouver l'instrument",
                                                      "details":null
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    public InstrumentResponseDTO getInstrument(
            @PathVariable
            final int id) throws RessourceNonTrouveException {
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
    @Operation(
            summary = "Modification d'un instrument",
            parameters = @Parameter(
                    name = "id",
                    description = "identifiant de l'instrument",
                    required = true,
                    example = "1"
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "l'instrument à enregistrer",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "idInstrument":1,
                                              "libelleInstrument":"Harpe"
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "idInstrument":1,
                                                      "libelleInstrument":"Harpe"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "idInstrument":null,
                                                      "libelleInstrument":"string"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "idInstrument":null,
                                                      "libelleInstrument":"string"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "idInstrument":null,
                                                      "libelleInstrument":"string"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Instrument> updateInstrument(
            @PathVariable
            final int id,
            @org.springframework.web.bind.annotation.RequestBody
            @Valid
            final Instrument instrument, final BindingResult result) {
        /*
        Instance d'instrument utilisée pour envoyer les messages d'erreur
        accompagnée du code retour BAD_REQUEST, NOT_FOUND,
        ou INTERNAL_SERVER_ERROR
        */
        /*Instrument errorResult = new Instrument();
        Optional<Instrument> optionalInstrument = service.getInstrument(id);
        if (optionalInstrument.isEmpty()) {
            // Aucun instrument n'a l'identifiant donné dans l'URL.
            errorResult.setLibelleInstrument("La ressource n'est pas "
                                             + "disponible.");
            return new ResponseEntity<>(errorResult,
                                        HttpStatus.NOT_FOUND);
        }
        Instrument currentInstrument = optionalInstrument.get();
        // Vérification de la correspondance entre l'identifiant de l'URL
        // et l'identifiant de l'instrument donné en paramètre.
        if (instrument.getIdInstrument() != id) {
            errorResult.setLibelleInstrument("L'instrument en paramètre n'a pas"
                                             + " le même identifiant");
            return new ResponseEntity<>(errorResult,
                                        HttpStatus.BAD_REQUEST);
        }
        // Récupèration des éventuelles erreurs de validation du libellé de
        // l'instrument
        FieldError error = result.getFieldError("libelleInstrument");
        if (error != null) {
            errorResult.setLibelleInstrument(error.getDefaultMessage());
            return new ResponseEntity<>(errorResult,
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
                    errorResult.setLibelleInstrument("Cet instrument existe "
                                                     + "déjà.");
                    return new ResponseEntity<>(errorResult,
                                                HttpStatus.BAD_REQUEST);
                } else {
                    errorResult.setLibelleInstrument("Erreur inconnue.");
                    return new ResponseEntity<>(
                            errorResult,
                            HttpStatus.INTERNAL_SERVER_ERROR
                    );
                }
            }
        }
        return new ResponseEntity<>(currentInstrument, HttpStatus.OK);*/
        return new ResponseEntity<>((Instrument) null, HttpStatus.OK);
    }

    /**
     * Endpoint de suppression d'un instrument.
     *
     * @param id l'identifiant de l'instrument à supprimer
     */
    @DeleteMapping("/instrument/{id}")
    @Operation(
            summary = "Suppression d'un instrument par son identifiant.",
            parameters = @Parameter(
                    name = "id",
                    description = "identifiant de l'instrument",
                    required = true,
                    example = "1"
            )
    )
    public void deleteInstrument(
            @PathVariable
            final int id) {
        service.deleteInstrument(id);
    }
}
