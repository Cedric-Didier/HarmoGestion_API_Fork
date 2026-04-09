package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Cours;
import fr.afpa.cda19.harmogestionapi.services.CoursService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controller pour les cours.
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 07/04/2026
 */
@RestController
public class CoursController {

    /**
     * Instance du service pour les cours.
     */
    private final CoursService coursService;


    /**
     * Constructeur du controller des cours.
     *
     * @param coursService instance du service des cours
     */
    @Autowired
    public CoursController(final CoursService coursService) {

        this.coursService = coursService;
    }


    /**
     * Pour une requête GET et une URL "/cours", retourne la liste des cours.
     *
     * @return Iterable{Cours} : liste des cours
     */
    @GetMapping("/cours")
    public Iterable<Cours> getAllCours() {

        return coursService.getAllCours();
    }

    /**
     * Pour une requête GET et une URL "/cours/{id},
     * retourne le cours correspondant à l'identifiant demandé.
     *
     * @param id int : identifiant du cours demandé.
     * @return Cours : le cours correspondant à l'id (null si inexistant)
     */
    @GetMapping("/cours/{id}")
    public Cours getCours(
            @PathVariable
            final int id) {

        Optional<Cours> cours = coursService.getCours(id);
        return cours.orElse(null);
    }

    /**
     * Pour une requête POST et une URL "/cours", crée le cours demandé.
     *
     * @param cours  Cours : cours à créer
     * @param result BindingResult : erreurs de validation par rapport au model
     * @return Réponse avec le cours créé et un statut 201,
     * ou un statut 400 ou 500 pour une erreur.
     */
    @PostMapping("/cours")
    public ResponseEntity<Cours> createCours(
            @RequestBody
            @Valid
            final Cours cours, final BindingResult result) {

        if (cours.getIdCours() != null || result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                Cours savedCours = coursService.saveCours(cours);
                return new ResponseEntity<>(savedCours, HttpStatus.CREATED);
            } catch (Exception _) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Pour une requête PUT et une URL "/cours/{id}", modifie le cours demandé.
     *
     * @param id     int : identifiant du cours à modifier
     * @param cours  Cours : cours à modifier
     * @param result BindingResult : erreurs de validation par rapport au model
     * @return Réponse avec le cours modifié avec un statut 200,
     * ou un statut 400 ou 500 pour une erreur.
     */
    @PutMapping("/cours/{id}")
    public ResponseEntity<Object> updateCours(
            @PathVariable
            final int id,
            @Valid
            @RequestBody
            final Cours cours, final BindingResult result) {

        Optional<Cours> optionalInstrument = coursService.getCours(id);
        if (optionalInstrument.isEmpty()) {
            // Aucun cours n'a l'identifiant donné dans l'URL.
            return new ResponseEntity<>("La ressource n'est pas disponible.",
                                        HttpStatus.NOT_FOUND);
        }
        if (getCours(id) == null || result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                Cours savedCours = coursService.saveCours(cours);
                return new ResponseEntity<>(savedCours, HttpStatus.OK);
            } catch (Exception _) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Pour une requête DELETE et une URL "/cours/{id}",
     * supprime le cours demandé.
     *
     * @param id int : identifiant du cours à supprimer.
     */
    @DeleteMapping("/cours/{id}")
    public void deleteCours(
            @PathVariable
            final int id) {

        coursService.deleteCours(id);
    }
}
