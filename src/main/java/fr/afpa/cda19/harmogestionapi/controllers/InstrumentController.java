package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.dtos.request.InstrumentCreateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.request.InstrumentUpdateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.response.ApiError;
import fr.afpa.cda19.harmogestionapi.dtos.response.InstrumentResponseDTO;
import fr.afpa.cda19.harmogestionapi.exceptions.IdNonCorrespondantException;
import fr.afpa.cda19.harmogestionapi.exceptions.RessourceDupliqueeException;
import fr.afpa.cda19.harmogestionapi.exceptions.RessourceNonTrouveException;
import fr.afpa.cda19.harmogestionapi.services.InstrumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
                            schema = @Schema(implementation = InstrumentResponseDTO.class)
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
                            schema = @Schema(implementation = InstrumentCreateRequestDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Instrument créé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = InstrumentResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Échec de la validation du DTO",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Duplication du nom d'un instrument",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
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
                                    schema = @Schema(implementation = InstrumentResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Impossible de trouver l'instrument",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
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
     * @param instrument Le DTO contenant l'instrument à modifier
     * @return L'instrument modifié
     * @throws RessourceNonTrouveException levée si l'instrument n'existe pas
     * @throws RessourceDupliqueeException levée en cas de duplication du nom de l'instrument
     * @throws IdNonCorrespondantException levée si l'id du DTO et le paramètre 'id' ne
     *                                     correspondent pas
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
                            schema = @Schema(implementation = InstrumentUpdateRequestDTO.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "id": 1,
                                              "nom": "Harpe"
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
                                    schema = @Schema(implementation = InstrumentResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Échec de la validation du DTO",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Impossible de trouver l'instrument",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Duplication du nom d'un instrument",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    public ResponseEntity<InstrumentResponseDTO> updateInstrument(
            @PathVariable
            final int id,
            @org.springframework.web.bind.annotation.RequestBody
            @Valid
            final InstrumentUpdateRequestDTO instrument)
            throws RessourceDupliqueeException, IdNonCorrespondantException,
            RessourceNonTrouveException {
        return new ResponseEntity<>(service.updateInstrument(instrument, id), HttpStatus.OK);
    }

    /**
     * Endpoint de suppression d'un instrument.
     *
     * @param id l'identifiant de l'instrument à supprimer
     * @throws RessourceNonTrouveException levée si l'instrument n'existe pas
     */
    @DeleteMapping("/instrument/{id}")
    @Operation(
            summary = "Suppression d'un instrument par son identifiant.",
            parameters = @Parameter(
                    name = "id",
                    description = "identifiant de l'instrument",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Instrument supprimé"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Impossible de trouver l'instrument",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    public void deleteInstrument(
            @PathVariable
            final int id) throws RessourceNonTrouveException {
        service.deleteInstrument(id);
    }
}
