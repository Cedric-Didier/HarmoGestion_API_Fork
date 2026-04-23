package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.models.Pratiquer;
import fr.afpa.cda19.harmogestionapi.models.PratiquerPK;
import fr.afpa.cda19.harmogestionapi.repositories.PratiquerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service de liaison entre les contrôleurs et le repository
 * {@link PratiquerRepository}.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 15/04/2026
 */
@Service
public class PratiquerService {

    /**
     * Repository des instruments pratiqués.
     */
    private final PratiquerRepository repository;

    /**
     * Initialisation du service.
     *
     * @param repository repository des instruments pratiqués
     */
    @Autowired
    public PratiquerService(
            PratiquerRepository repository) {
        this.repository = repository;
    }

    /**
     * Récupèration de la liste des instruments pratiqués par les membres.
     *
     * @return la liste des instruments pratiqués
     */
    public Iterable<Pratiquer> getInstrumentsPratiques() {
        return repository.findAll();
    }

    /**
     * Récupèration de la liste des instruments pratiqués par un membre.
     *
     * @param idMembre L'identifiant du membre dont on souhaite la liste
     *                 des instruments pratiqués
     * @return La liste des instruments pratiqués par le membre
     */
    public Iterable<Pratiquer> getInstrumentsPratiquesByIdMembre(
            final Integer idMembre) {
        return repository.findByIdMembre(idMembre);
    }

    /**
     * Récupèration de la liste des membres pratiquant d'un instrument.
     *
     * @param idInstrument l'identifiant de l'instrument dont on souhaite
     *                     connaitre les pratiquants
     * @return La liste des membres pratiquant de cet instrument
     */
    public Iterable<Pratiquer> getMembresPratiquantsByIdInstrument(
            final Integer idInstrument) {
        return repository.findByIdInstrument(idInstrument);
    }

    /**
     * Récupèration du niveau de pratique d'un instrument par un membre.
     *
     * @param id ({@link PratiquerPK}) l'ensemble {Instrument, Membre}
     *           constituant l'identifiant de la table
     * @return L'éventuel niveau de pratique correspondant à l'identifiant
     * fourni.
     */
    public Optional<Pratiquer> getInstrumentPratiqueById(final PratiquerPK id) {
        return repository.findById(id);
    }

    /**
     * Sauvegarde du niveau de pratique d'un instrument par un membre.
     *
     * @param pratiquer ({@link Pratiquer}) le niveau de pratique à sauvegarder
     * @return le niveau de pratique sauvegardé
     */
    @Transactional
    public Pratiquer savePratiqueInstrument(final Pratiquer pratiquer) {
        return repository.save(pratiquer);
    }

    /**
     * Supprime le niveau de pratique d'un instrument pour un membre.
     *
     * @param id ({@link PratiquerPK}) l'ensemble {Instrument, Membre}
     *           constituant l'identifiant de la table
     */
    @Transactional
    public void deletePratiqueInstrument(final PratiquerPK id) {
        repository.deleteById(id);
    }

    /**
     * Supprime la liste des instruments pratiqués d'un membre.
     *
     * @param idMembre L'identifiant du membre dont on souhaite supprimer
     *                 la liste des instruments pratiqués
     */
    @Transactional
    public void deleteAllPratiqueInstrumentByMembre(final Integer idMembre) {
        repository.deleteAllByIdMembre(idMembre);
    }

    /**
     * Supprime la liste des membres pratiquants d'un instrument.
     *
     * @param idInstrument L'identifiant de l'instrument dont on souhaite
     *                     supprimer la liste des membres pratiquants
     */
    @Transactional
    public void deleteAllPratiquantByInstrument(final Integer idInstrument) {
        repository.deleteAllByIdInstrument(idInstrument);
    }

    /**
     * Vérifie qu'un membre pratique d'un instrument.
     *
     * @param id L'identifiant composé de l'identifiant du membre et
     *           de l'identifiant de l'instrument
     * @return True si ce membre pratique cet instrument
     */
    public boolean pratiqueExistById(final PratiquerPK id) {
        return repository.existsById(id);
    }
}
