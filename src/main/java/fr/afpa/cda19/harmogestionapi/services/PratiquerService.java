package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.models.Pratiquer;
import fr.afpa.cda19.harmogestionapi.models.PratiquerPK;
import fr.afpa.cda19.harmogestionapi.repositories.PratiquerRepository;
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
    public PratiquerService(
            @Autowired
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
     * @param membre Le membre dont on souhaite la liste des instruments
     *               pratiqués
     * @return La liste des instruments pratiqués par le membre
     */
    public Iterable<Pratiquer> getInstrumentsPratiquesByMembre(
            final Membre membre) {
        return repository.findByMembre(membre);
    }

    /**
     * Récupèration de la liste des membres pratiquant d'un instrument.
     *
     * @param instrument l'instrument dont on souhaite connaitre les pratiquants
     * @return La liste des membres pratiquant de cet instrument
     */
    public Iterable<Pratiquer> getMembresByInstrumentPratique(
            final Instrument instrument) {
        return repository.findByInstrument(instrument);
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
    public Pratiquer savePratiqueInstrument(final Pratiquer pratiquer) {
        return repository.save(pratiquer);
    }

    /**
     * Supprime le niveau de pratique d'un instrument pour un membre.
     *
     * @param id ({@link PratiquerPK}) l'ensemble {Instrument, Membre}
     *           constituant l'identifiant de la table
     */
    public void deletePratiqueInstrument(final PratiquerPK id) {
        repository.deleteById(id);
    }

    /**
     * Supprime la liste des instruments pratiqués d'un membre.
     *
     * @param membre Le membre dont on souhaite supprimer la liste des
     *               instruments pratiqués
     */
    public void deleteAllPratiqueInstrumentByMembre(final Membre membre) {
        repository.deleteAllByMembre(membre);
    }

    /**
     * Supprime la liste des membres pratiquants d'un instrument.
     *
     * @param instrument L'instrument dont on souhaite supprimer la liste des
     *                   membres pratiquants
     */
    public void deleteAllPratiquantByInstrument(final Instrument instrument) {
        repository.deleteAllByInstrument(instrument);
    }
}
