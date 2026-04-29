package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.models.Cours;
import fr.afpa.cda19.harmogestionapi.repositories.CoursRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service pour les cours.
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 07/04/2026
 */
@Data
@Service
public class CoursService {

    /**
     * Instance de la repository des cours.
     */
    private CoursRepository coursRepository;


    /**
     * Constructeur du service des cours.
     *
     * @param coursRepository instance de la repository des cours
     */
    @Autowired
    public CoursService(final CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }


    /**
     * Service pour chercher tous les cours.
     *
     * @return Iterable{Cours} : la liste des cours
     */
    public Iterable<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    /**
     * Service pour chercher un cours selon son identifiant.
     *
     * @param id int : identifiant du cours cherché
     * @return Optional{Cours} : le cours correspondant à l'id (peut être null)
     */
    public Optional<Cours> getCours(final int id) {
        return coursRepository.findById(id);
    }

    /**
     * Service pour créer ou modifier un cours.
     *
     * @param cours Cours : cours à créer/modifier
     * @return Cours : le cours créé/modifié
     */
    public Cours saveCours(final Cours cours) {
        return coursRepository.save(cours);
    }

    /**
     * Service pour supprimer un cours selon son identifiant.
     *
     * @param id int : identifiant du cours à supprimer
     */
    public void deleteCours(final int id) {
        coursRepository.deleteById(id);
    }
}
