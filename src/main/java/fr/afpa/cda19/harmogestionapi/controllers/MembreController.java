package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.services.MembreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.time.LocalDate;
import java.util.Optional;

/**
 * Contrôleur de gestion des requêtes concernant les membres.
 *
 * @author Rodolphe BRUCKER
 * @version 1.0.0
 * @since 10/04/2026
 */
@RestController
@Tag(name = "Membres", description = "Gestion des membres")
public class MembreController {
    //==== Variables ====

    /**
     * Service de liaison avec le repository des membres.
     */
    private final MembreService service;

    //==== Constructeurs ====

    /**
     * Constructeur d'initialisation du contrôleur.
     *
     * @param service Sevice de liaison avec le repository des membres.
     */
    @Autowired
    public MembreController(final MembreService service) {
        this.service = service;
    }

    //==== Méthodes ====

    /**
     * Endpoint de récupèration de la liste des membres.
     *
     * @return la liste récupérée
     */
    @GetMapping("/membres")
    @Operation(
            summary = "Liste des membres",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    useReturnTypeSchema = true
            )
    )
    public Iterable<Membre> getMembres() {
        return service.getMembres();
    }

    /**
     * Endpoint de création d'un nouveau membre.
     *
     * @param membre Membre à créer.
     * @param result Résultat de la validation du membre.
     * @return La response contenant le membre créé avec un code 201
     * ou un message d'erreur avec un code 400 ou 500.
     */
    @PostMapping("/membre")
    @Operation(
            summary = "Création d'un membre",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Le membre à enregistrer",
                    useParameterTypeSchema = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            useReturnTypeSchema = true
                    )
            }
    )
    public ResponseEntity<Object> createMembre(
            @RequestBody
            @Valid
            final Membre membre, final BindingResult result) {
        if (membre.getIdMembre() != null) {
            //Le membre dans la requête ne doit pas avoir d'identifiant
            return new ResponseEntity<>((Membre) null,
                                        HttpStatus.BAD_REQUEST);
        }
        //Récupération d'éventuelles erreurs de validation du membre
        StringBuilder messagesErreur = new StringBuilder();
        FieldError erreursNom = result.getFieldError("nomMembre");
        FieldError erreursPrenom = result.getFieldError("prenomMembre");
        FieldError erreursDateInscription = result.getFieldError("dateInscriptionMembre");
        if (erreursNom != null) {
            messagesErreur.append(erreursNom.getDefaultMessage()).append(" | ");
        }
        if (erreursPrenom != null) {
            messagesErreur.append(erreursPrenom.getDefaultMessage()).append(" | ");
        }
        if (erreursDateInscription != null) {
            messagesErreur.append(erreursDateInscription.getDefaultMessage()).append(" | ");
        }
        if (!messagesErreur.isEmpty()) {
            return new ResponseEntity<>(messagesErreur.toString(), HttpStatus.BAD_REQUEST);
        }
        //Enregistrement du membre
        try {
            Membre savedMembre = service.saveMembre(membre);
            return new ResponseEntity<>(savedMembre, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException dive) {
            SQLException sqle = (SQLException) dive.getRootCause();
            if (sqle != null && sqle.getErrorCode() == 1062) {
                // Violation de l'unicité de l'identifiant
                return new ResponseEntity<>("Cet identifiant a déjà été attribué.",
                                            HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Erreur inconnue.",
                                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Endpoint de récupèration d'un membre par son identifiant.
     *
     * @param id Identifiant du membre à récupérer.
     * @return Réponse contenant le membre correspondant avec un code 200
     * ou message d'erreur avec un code 404.
     */
    @GetMapping("/membre/{id}")
    @Operation(
            summary = "Récupèration d'un membre par son identifiant",
            parameters = @Parameter(
                    name = "id",
                    description = "identifiant du membre",
                    required = true,
                    example = "1"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    useReturnTypeSchema = true
            )
    )
    public Optional<Membre> getMembre(
            @PathVariable
            final int id) {
        return service.getMembre(id);
    }

    /**
     * Endpoint de mise à jour d'un membre.
     *
     * @param id     Identifiant du membre à modifier
     * @param membre Membre modifié.
     * @param result Résultat de la validation du membre modifié.
     * @return Réponse contenant le membre modifié avec un code 200
     * ou un message d'erreur avec un code 400 ou 500
     */
    @PutMapping("/membre/{id}")
    @Operation(
            summary = "Modification d'un membre",
            parameters = @Parameter(
                    name = "id",
                    description = "identifiant du membre",
                    required = true,
                    example = "1"
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Le membre à enregistrer",
                    useParameterTypeSchema = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            useReturnTypeSchema = true
                    )
            }
    )
    public ResponseEntity<Object> updateMembre(
            @PathVariable
            final int id,
            @RequestBody
            @Valid
            final Membre membre, final BindingResult result) {
        Optional<Membre> optionalMembre = service.getMembre(id);
        if (optionalMembre.isEmpty()) {
            // Aucun membre n'a l'identifiant donné dans l'URL.
            return new ResponseEntity<>("La ressource n'est pas disponible.",
                                        HttpStatus.NOT_FOUND);
        }
        Membre currentMembre = optionalMembre.get();
        //Vérification de la correspondance entre l'identifiant de l'URL
        //et l'identifiant du membre donné en paramètre.
        if (membre.getIdMembre() != id) {
            return new ResponseEntity<>("Le membre en paramètre n'a pas "
                                        + "le même identifiant.",
                                        HttpStatus.BAD_REQUEST);
        }
        //Récupèration des éventuelles erreurs de validation du libellé du membre
        //Récupération d'éventuelles erreurs de validation du membre
        StringBuilder messagesErreur = new StringBuilder();
        FieldError erreursNom = result.getFieldError("nomMembre");
        FieldError erreursPrenom = result.getFieldError("prenomMembre");
        FieldError erreursDateInscription = result.getFieldError("dateInscriptionMembre");
        if (erreursNom != null) {
            messagesErreur.append(erreursNom.getDefaultMessage()).append(" | ");
        }
        if (erreursPrenom != null) {
            messagesErreur.append(erreursPrenom.getDefaultMessage()).append(" | ");
        }
        if (erreursDateInscription != null) {
            messagesErreur.append(erreursDateInscription.getDefaultMessage()).append(" | ");
        }
        if (!messagesErreur.isEmpty()) {
            return new ResponseEntity<>(messagesErreur.toString(), HttpStatus.BAD_REQUEST);
        }
        //Vérification de la présence d'une modification du membre
        String nomMembre = membre.getNomMembre();
        String prenomMembre = membre.getPrenomMembre();
        LocalDate dateInscriptionMembre = membre.getDateInscriptionMembre();
        if (nomMembre != null && nomMembre.compareTo(
                currentMembre.getNomMembre()) != 0) {
            currentMembre.setNomMembre(nomMembre);
        }
        if (prenomMembre != null && prenomMembre.compareTo(
                currentMembre.getPrenomMembre()) != 0) {
            currentMembre.setPrenomMembre(prenomMembre);
        }
        if (dateInscriptionMembre != null &&
            !dateInscriptionMembre.isEqual(currentMembre.getDateInscriptionMembre())) {
            currentMembre.setDateInscriptionMembre(dateInscriptionMembre);
        }
        currentMembre.setPratiques(membre.getPratiques());
        try {
            service.saveMembre(currentMembre);
        } catch (DataIntegrityViolationException _) {
            return new ResponseEntity<>(
                    "Erreur inconnue.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(currentMembre, HttpStatus.OK);
    }

    /**
     * Endpoint de suppression d'un membre.
     *
     * @param id Identifiant du membre à supprimer
     */
    @DeleteMapping("/membre/{id}")
    @Operation(
            summary = "Suppression d'un membre par son identifiant",
            parameters = @Parameter(
                    name = "id",
                    description = "identifiant du membre",
                    required = true,
                    example = "1"
            )
    )
    public void deleteMembre(
            @PathVariable
            final int id) {
        service.deleteMembre(id);
    }
}
