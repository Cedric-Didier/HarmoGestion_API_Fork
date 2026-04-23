package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.repositories.MembreRepository;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.repositories.PratiquerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service de liaison entre les contrôleurs et le repository
 * {@link MembreRepository}.
 *
 * @author Rodolphe BRUCKER
 * @version 1.0.0
 * @since 09/04/2026
 */
@Service
public class MembreService {

    /**
     * Service de liaison avec le repository des instruments.
     */
    private final MembreRepository repository;

    /**
     * Repository de la pratique des instruments
     */
    private final PratiquerRepository pratiquerRepository;

    /**
     * Constructeur.
     *
     * @param repository Repository des membres.
     */
    @Autowired
    public MembreService(final MembreRepository repository,
                         final PratiquerRepository pratiquerRepository) {
        this.repository = repository;
        this.pratiquerRepository = pratiquerRepository;
    }

    /**
     * Récupèration d'un membre par son identifiant.
     *
     * @param id l'identifiant du membre recherché
     * @return ({@link Optional}) Éventuel membre correspondant à l'identifiant.
     */
    public Optional<Membre> getMembre(int id) {
        return repository.findById(id);
    }

    /**
     * Récupèration de la liste des membres.
     *
     * @return Liste des membres.
     */
    public Iterable<Membre> getMembres() {
        return repository.findAll();
    }

    /**
     * Suppression d'un membre par son identifiant.
     *
     * @param id Identifiant du membre à supprimer.
     */
    @Transactional
    public void deleteMembre(int id) {
        pratiquerRepository.deleteAllByIdMembre(id);
        repository.deleteById(id);
    }

    /**
     * Enregistrement ou modification d'un membre.
     *
     * @param membre Membre à créer ou modifier.
     * @return Membre après création ou modification.
     */
    @Transactional
    public Membre saveMembre(Membre membre) {
        return repository.save(membre);
    }
}
